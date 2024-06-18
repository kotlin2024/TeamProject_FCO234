package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.dto.response.ChannelMemberPositionResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.entity.ChannelMemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.repository.ChannelMemberPositionRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelAlreadyExistentException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class ChannelMemberPositionService (

    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val channelMemberPositionRepository: ChannelMemberPositionRepository
) {

    fun subscribeChannel(
        channelId: Long
    ): ChannelMemberPositionResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel")

        if(channelMemberPositionRepository.existsByChannelIdAndMemberId(channelId, member.id!!))
            throw ModelAlreadyExistentException("ChannelMemberPosition")


        return ChannelMemberPositionResponse.from(
            channelMemberPositionRepository.save(
                ChannelMemberPosition(
                    channel = targetChannel,
                    member = member,
                )
            )
        )
    }

    fun getChannelMembers(
        channelId: Long
    ): List<ChannelMemberPositionResponse> {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        return channelMemberPositionRepository.findAllByChannelId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel")
        ).map { ChannelMemberPositionResponse.from(it) }
    }


    fun assignChannelManager(
        channelId: Long,
        memberId: Long
    ): ChannelMemberPositionResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val assigner = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")


        val targetChannelMemberPosition = channelMemberPositionRepository.findByChannelIdAndMemberId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            memberRepository.findByIdOrNull(memberId)?.id
                ?: throw ModelNotFoundException("Member")
        ) ?: throw ModelNotFoundException("ChannelMemberPosition")

        targetChannelMemberPosition.position = MemberPosition.CHANNEL_MANAGER

        return ChannelMemberPositionResponse.from(
            channelMemberPositionRepository.save(targetChannelMemberPosition)
        )
    }


    fun blockMember(
        channelId: Long,
        memberId: Long
    ): ChannelMemberPositionResponse {

        val targetChannelMemberPosition = channelMemberPositionRepository.findByChannelIdAndMemberId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            memberRepository.findByIdOrNull(memberId)?.id
                ?: throw ModelNotFoundException("Member")
        ) ?: throw ModelNotFoundException("ChannelMemberPosition")

        targetChannelMemberPosition.position = MemberPosition.CHANNEL_BLACKLIST

        return ChannelMemberPositionResponse.from(
            channelMemberPositionRepository.save(targetChannelMemberPosition)
        )
    }


    fun acceptMember(
        channelId: Long,
        memberId: Long
    ): ChannelMemberPositionResponse {

        val targetChannelMemberPosition = channelMemberPositionRepository.findByChannelIdAndMemberId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            memberRepository.findByIdOrNull(memberId)?.id
                ?: throw ModelNotFoundException("Member")
        ) ?: throw ModelNotFoundException("ChannelMemberPosition")

        targetChannelMemberPosition.position = MemberPosition.CHANNEL_MEMBER

        return ChannelMemberPositionResponse.from(
            channelMemberPositionRepository.save(targetChannelMemberPosition)
        )
    }
}