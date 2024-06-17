package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelStatus
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.entity.ChannelMemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.repository.ChannelMemberPositionRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelAlreadyExistentException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class ChannelService (

    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val channelMemberPositionRepository: ChannelMemberPositionRepository
) {

    @Transactional
    fun createChannel(
        request: CreateChannelRequest
    ): ChannelResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw ModelNotFoundException("Member")

        if (channelRepository.existsByProfileGameTitle(request.gameTitle))
            throw ModelAlreadyExistentException("Channel")

        val createdChannel = Channel(
            profile = CreateChannelRequest.toProfile(request),
            administrator = member
        )
        channelRepository.save(createdChannel)

        val channelAdmin = ChannelMemberPosition(
            channel = createdChannel,
            member = member
        )

        channelMemberPositionRepository.save(
            ChannelMemberPosition(
                channel = createdChannel,
                member = member,
                position = MemberPosition.CHANNEL_ADMIN
            )
        )

        return ChannelResponse.from(createdChannel)
    }


    fun getAllChannels(): List<ChannelResponse> =
        channelRepository.findAllByStatus(ChannelStatus.OPEN)
            .map { ChannelResponse.from(it) }


    fun getChannel(
        channelId: Long
    ): ChannelResponse =

        ChannelResponse.from(
            channelRepository.findByIdOrNull(channelId)
                ?: throw ModelNotFoundException("Channel")
        )


    fun updateChannel(
        channelId: Long,
        request: UpdateChannelRequest
    ): ChannelResponse {

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel")

        targetChannel.updateFrom(request)

        return ChannelResponse.from(
            channelRepository.save(targetChannel)
        )
    }


    fun deleteChannel(
        channelId: Long
    ) {

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel")

        channelRepository.delete(targetChannel)
    }

}