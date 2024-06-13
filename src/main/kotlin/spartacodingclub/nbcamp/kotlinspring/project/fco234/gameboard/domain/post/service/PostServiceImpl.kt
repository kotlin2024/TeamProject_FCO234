package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.exception.ModelNotfoundException

@Service
class PostServiceImpl(
    private val postRepository: PostRepository
) : PostService {


    override fun getAllPosts(): List<PostResponse> {

        return postRepository.findAll().map{it.toResponse()}
    }


    override fun getPost(id: Long): PostResponse {

        val post =  postRepository.findByIdOrNull(id)?:throw ModelNotfoundException("Post",id)
        return post.toResponse()

    }

    override fun createPost(createPostRequest: CreatePostRequest): PostResponse {
        val createdPost = Post(
            title = createPostRequest.title,
            content = createPostRequest.content)

        return postRepository.save(createdPost).toResponse()
    }

    override fun updatePost(id: Long, updatePostRequest: UpdatePostRequest): PostResponse {
        val updatePost = postRepository.findByIdOrNull(id = id) ?: throw ModelNotfoundException("Post",id)
        val (title, content) = updatePostRequest

        updatePost.title = title
        updatePost.content = content

        return postRepository.save(updatePost).toResponse()

    }


    override fun deletePost(id: Long) {
        postRepository.findByIdOrNull(id) ?: throw ModelNotfoundException("Post",id)
        postRepository.deleteById(id)
    }
}
