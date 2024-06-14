package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service.AuthService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.UserRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.repository.UserRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.exception.ModelNotFoundException

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val commentRepository: CommentRepository
) : PostService {



    override fun getAllPosts(): List<PostResponse> {

        return postRepository.findAll().map{it.toResponse()}
    }


    override fun getPost(id: Long): PostResponse {

        val post =  postRepository.findByIdOrNull(id)?:throw ModelNotFoundException("Post",id)
        return post.toResponse()

    }




    override fun createPost(createPostRequest: CreatePostRequest): PostResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal

        val user = userRepository.findByEmail(principal.email)
            ?: throw RuntimeException("너 누구야")

        val createdPost = Post(
            title = createPostRequest.title,
            content = createPostRequest.content,
            user= user
            )

        return postRepository.save(createdPost).toResponse()
    }

    override fun updatePost(id: Long, updatePost: UpdatePostRequest): PostResponse {
        val updatePosts = postRepository.findByIdOrNull(id = id) ?: throw RuntimeException("아몰라")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal


        val user = userRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")

        if(updatePosts.user!!.id !=user.id  && (user.role == UserRole.PLATFORM_USER ||(user.role== UserRole.CHANNEL_USER ))) throw RuntimeException("작성한 본인만 수정 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")


        val (title, content) = updatePost

        updatePosts.title = title
        updatePosts.content = content

        return postRepository.save(updatePosts).toResponse()

    }


    override fun deletePost(postId: Long) {
        val post = postRepository.findById(postId)
            .orElseThrow { EntityNotFoundException("Post not found with id $postId") }

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal

        val user = userRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")


        if(post.user!!.id !=user.id && (user.role == UserRole.PLATFORM_USER ||(user.role== UserRole.CHANNEL_USER )) ) throw RuntimeException("작성한 본인만 삭제 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")



        commentRepository.deleteAll(commentRepository.findAllByPostId(postId))

        postRepository.delete(post)
    }
}
