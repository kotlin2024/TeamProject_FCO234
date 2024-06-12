package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
)