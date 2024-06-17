package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request

data class UpdateChannelRequest (

    val title: String,
    val description: String?,
    val ageLimit: Int
)