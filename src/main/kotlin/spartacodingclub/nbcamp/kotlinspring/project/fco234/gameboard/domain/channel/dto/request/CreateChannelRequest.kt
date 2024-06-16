package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request

data class CreateChannelRequest(
    val title: String,
    val description: String?,
    val gameTitle: String,
    val ageLimit: Int
)
