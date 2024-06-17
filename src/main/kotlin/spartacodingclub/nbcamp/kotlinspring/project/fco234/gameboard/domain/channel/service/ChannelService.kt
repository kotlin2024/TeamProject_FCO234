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
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelAlreadyExistentException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service.IGDBService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class ChannelService (

    private val memberRepository: MemberRepository,
    private val channelMemberPositionRepository: ChannelMemberPositionRepository,
    private val channelRepository: ChannelRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val igdbService: IGDBService
) {

    @Transactional
    fun createChannel(
        request: CreateChannelRequest,
        accessToken: String
    ): ChannelResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw ModelNotFoundException("Member")

        if (channelRepository.existsByProfileGameTitle(request.gameTitle))
            throw ModelAlreadyExistentException("Channel")

        if(igdbService.checkGameExists(accessToken, request.gameTitle)){
            throw IllegalStateException("IGDB에서 확인 된 게임명을 정확하게 입력해주세요")
        }

        val createdChannel = Channel(
            profile = CreateChannelRequest.toProfile(request),
            administrator = member
        )
        channelRepository.save(createdChannel)

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


    @Transactional
    fun deleteChannel(
        channelId: Long
    ) {

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel")
        val posts = postRepository.findAllByChannelId(channelId)
        val comments = mutableListOf<Comment>()

        for (each in posts)
            comments += commentRepository.findAllByPostId(each.id!!)

        commentRepository.deleteAll(comments)
        postRepository.deleteAll(posts)
        channelRepository.delete(targetChannel)
    }

}