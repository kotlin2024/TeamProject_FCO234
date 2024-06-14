package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

import java.time.LocalDateTime

data class ChannelResponse(
    val id: Long,
    val title: String,
    val description: String,
    var ageLimit: Int,
    var gameTitle: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
