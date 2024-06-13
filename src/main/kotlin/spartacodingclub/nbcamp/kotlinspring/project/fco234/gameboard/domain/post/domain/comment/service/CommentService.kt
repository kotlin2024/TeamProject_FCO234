package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.service

import org.springframework.data.repository.findByIdOrNull
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.model.toResponse
import jakarta.transaction.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.exception.ModelNotfoundException

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {

    fun getComment(postId: Long): List<CommentResponse>{
        if(!postRepository.existsById(postId)) throw ModelNotfoundException("Post", postId)
        return commentRepository.findAllByPostId(postId).map{ it.toResponse()}
    }


    @Transactional
    fun createComment(postId:Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotfoundException("Post", postId)
//        val comment= commentRepository.findByIdOrNull(postId) ?: throw RuntimeException("몰라")

        return commentRepository.save(
            Comment(
            content = createCommentRequest.content,
            post = post
        )
        ).toResponse()
    }


    @Transactional
    fun updateComment(postId:Long, commentId:Long, updateCommentRequest: UpdateCommentRequest): CommentResponse {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotfoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotfoundException("Comment", commentId)

        comment.content = updateCommentRequest.content
        return commentRepository.save(comment).toResponse()

    }

    @Transactional
    fun deleteComment(postId: Long,commentId: Long){
        postRepository.findByIdOrNull(postId) ?: throw ModelNotfoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotfoundException("Comment", commentId)

        commentRepository.delete(comment)
    }


}

