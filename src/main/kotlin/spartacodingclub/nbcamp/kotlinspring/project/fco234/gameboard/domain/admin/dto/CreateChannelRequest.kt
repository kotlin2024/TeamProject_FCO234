package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

data class CreateChannelRequest(
    val title: String,
    val description: String?,
    val gameTitle: String,
    val ageLimit: Int
)
