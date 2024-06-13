package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto

data class WarnChannelRequest (
    val reason: String,
    val source: String,
    val targetId: Long,
)
