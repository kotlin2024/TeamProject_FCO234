package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.entity.ChannelMemberPosition

data class ChannelMemberPositionResponse(

    val id: Long,

    val channelId: Long,
    val memberId: Long,
    val position: String,
) {

    companion object {

        fun from(position: ChannelMemberPosition) =
            ChannelMemberPositionResponse(
                id = position.id!!,
                channelId = position.channel.id!!,
                memberId = position.member.id!!,
                position = position.position.toString()
        )
    }

}
