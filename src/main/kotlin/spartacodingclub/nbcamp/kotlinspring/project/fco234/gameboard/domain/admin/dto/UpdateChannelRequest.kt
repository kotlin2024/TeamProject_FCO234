package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

data class UpdateChannelRequest(
    val title: String,
    val description: String?,
    val gameTitle: String,
    val ageLimit: Int
)
