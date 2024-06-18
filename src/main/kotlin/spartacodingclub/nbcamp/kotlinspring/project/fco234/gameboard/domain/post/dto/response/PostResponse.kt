package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class PostResponse (

    val id: Long,
    val title: String,
    val content: String,

    val authorId: Long,
    val channelId: Long,

    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {

    companion object {
        fun from(post: Post) = PostResponse(
            id = post.id!!,
            title = post.title,
            content = post.content,
            authorId = post.author.id!!,
            channelId = post.channel.id!!,
            createdAt = post.time.createdAt,
            updatedAt = post.time.updatedAt
        )
    }
}