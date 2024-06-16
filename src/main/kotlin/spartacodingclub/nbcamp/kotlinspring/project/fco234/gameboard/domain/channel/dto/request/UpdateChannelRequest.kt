package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request

data class UpdateChannelRequest(
    val title: String,
    val description: String?,
    val gameTitle: String,
    val ageLimit: Int
)
