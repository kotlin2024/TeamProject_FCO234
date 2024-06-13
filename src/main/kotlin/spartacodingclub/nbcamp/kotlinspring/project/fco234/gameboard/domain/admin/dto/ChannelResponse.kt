package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

import java.time.LocalDateTime

data class ChannelResponse(
    val id: Long,
    val channelId: Long,
    val description: String,
    var ageLimit: Int,
    var gameTheme: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
