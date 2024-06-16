package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service.AuthService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException

@Service
class PostService (

    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val authService: AuthService,
    private val commentRepository: CommentRepository
) {

    fun getAllPosts():
            List<PostResponse> {

        return postRepository.findAll().map{it.toResponse()}
    }


    fun getPost(
        id: Long
    ): PostResponse {

        val post =  postRepository.findByIdOrNull(id)?:throw ModelNotFoundException("Post",id)
        return post.toResponse()
    }


    fun createPost(
        createPostRequest: CreatePostRequest
    ): PostResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal

        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("너 누구야")

        val createdPost = Post(
            title = createPostRequest.title,
            content = createPostRequest.content,
            member= user
            )

        return postRepository.save(createdPost).toResponse()
    }


    fun updatePost(
        id: Long, updatePost: UpdatePostRequest
    ): PostResponse {

        val updatePosts = postRepository.findByIdOrNull(id = id) ?: throw RuntimeException("아몰라")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal


        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")

        if(updatePosts.member!!.id !=user.id && (user.role == MemberRole.PLATFORM_USER || (user.role == MemberRole.CHANNEL_USER)))
            throw RuntimeException("작성한 본인만 수정 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")

        val (title, content) = updatePost

        updatePosts.title = title
        updatePosts.content = content

        return postRepository.save(updatePosts).toResponse()
    }


    fun deletePost(
        postId: Long
    ) {

        val post = postRepository.findByIdOrNull(postId)
            ?: throw EntityNotFoundException("Post not found with id $postId")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal

        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")

        if(post.member!!.id !=user.id && (user.role == MemberRole.PLATFORM_USER || (user.role == MemberRole.CHANNEL_USER)))
            throw RuntimeException("작성한 본인만 삭제 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")

        commentRepository.deleteAll(commentRepository.findAllByPostId(postId))

        postRepository.delete(post)
    }

}