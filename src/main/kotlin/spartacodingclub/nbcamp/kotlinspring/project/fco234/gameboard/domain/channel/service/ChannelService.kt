package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelProfile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelStatus
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundExceptionNew
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class ChannelService (

    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
) {

    fun createChannel(
        request: CreateChannelRequest
    ): ChannelResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")

        val createdChannel = Channel(
            profile = CreateChannelRequest.toProfile(request),
            administrator = member
        )

        return ChannelResponse.from(channelRepository.save(createdChannel))
    }

    fun getAllChannels(): List<ChannelResponse> =
        channelRepository.findAllByStatus(ChannelStatus.OPEN).map { ChannelResponse.from(it) }

    fun getChannel(
        channelId: Long
    ): ChannelResponse =

        ChannelResponse.from(
            channelRepository.findByIdOrNull(channelId)
                ?: throw ModelNotFoundExceptionNew("Channel")
        )

}

