package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import java.time.ZonedDateTime

data class ChannelResponse (

    val id: Long,

    val title: String,
    val description: String,
    var ageLimit: Int,
    var gameTitle: String,

    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {

    companion object {
        fun from(channel: Channel) = ChannelResponse(
            id = channel.id!!,
            title = channel.profile.title,
            description = channel.profile.description,
            ageLimit = channel.profile.ageLimit,
            gameTitle = channel.profile.gameTitle,
            createdAt = channel.time.createdAt,
            updatedAt = channel.time.updatedAt
        )
    }
}