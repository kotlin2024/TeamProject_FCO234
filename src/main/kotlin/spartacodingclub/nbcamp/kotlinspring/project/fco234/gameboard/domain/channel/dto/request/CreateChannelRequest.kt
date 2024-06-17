package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelProfile

data class CreateChannelRequest (

    val title: String,
    val description: String?,
    val gameTitle: String,
    val ageLimit: Int
) {
    companion object {
        fun toProfile(request: CreateChannelRequest) = ChannelProfile(
            title = request.title,
            description = request.description ?: "",
            gameTitle = request.gameTitle,
            ageLimit = request.ageLimit
        )
    }
}