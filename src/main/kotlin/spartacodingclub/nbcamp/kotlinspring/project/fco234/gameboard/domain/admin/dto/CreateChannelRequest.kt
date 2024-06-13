package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

data class CreateChannelRequest(
    val title: String,
    val description: String?,
    val gameTheme: String,
    val ageLimit: Int
)
