package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.warning.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.warning.entity.Warning
import java.time.ZonedDateTime

data class WarningResponse(

    val id: Long,
    val targetMemberId: Long,
    val sourceChannelId: Long,
    val sourceType: String,
    val sourceId: Long,
    val reason: String,
    val issuerId: Long,
    val createdAt: ZonedDateTime
) {

    companion object {

        fun from(warning: Warning) =

            WarningResponse(
                id = warning.id!!,
                targetMemberId = warning.targetMember.id!!,
                sourceChannelId = warning.source.sourceChannel.id!!,
                sourceType = warning.source.sourceType.toString(),
                sourceId = warning.source.sourceId,
                reason = warning.reason,
                issuerId = warning.issuer.id!!,
                createdAt = warning.createdAt
            )
    }
}