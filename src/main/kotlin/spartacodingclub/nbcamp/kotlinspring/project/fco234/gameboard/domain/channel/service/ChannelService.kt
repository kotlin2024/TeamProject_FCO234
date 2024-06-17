package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelStatus
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.repository.ChannelMemberPositionRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service.IGDBService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class ChannelService (

    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val channelMemberPositionRepository: ChannelMemberPositionRepository,
    private val igdbService: IGDBService
) {

    fun createChannel(
        request: CreateChannelRequest, accessToken:String): ChannelResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")

        if(igdbService.checkGameExists(accessToken, request.gameTitle)){
            throw IllegalStateException("IGDB에서 확인 된 게임명을 정확하게 입력해주세요")
        }

        val createdChannel = Channel(
            profile = CreateChannelRequest.toProfile(request),
            administrator = member
        )

        return ChannelResponse.from(channelRepository.save(createdChannel))
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