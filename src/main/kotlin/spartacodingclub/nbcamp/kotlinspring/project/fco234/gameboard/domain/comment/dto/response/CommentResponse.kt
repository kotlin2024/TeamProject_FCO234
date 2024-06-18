package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment
import java.time.ZonedDateTime

data class CommentResponse (

    val id: Long,
    val postId: Long,
    val content: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {

    companion object {
        fun from(comment: Comment) = CommentResponse (
            id = comment.id!!,
            postId = comment.post.id!!,
            content = comment.content,
            createdAt = comment.time.createdAt,
            updatedAt = comment.time.updatedAt
        )
    }

}