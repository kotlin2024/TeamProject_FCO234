package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request.WarnChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.AdminChannel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.ChannelManager
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.Type
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.Warning
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository.ChannelManagerRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository.WarningRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository

@Service
class ChannelAdminService (

    private val channelRepository: ChannelRepository,
    private val warningRepository: WarningRepository,
    private val memberRepository: MemberRepository,
    private val channelManagerRepository: ChannelManagerRepository
) {

    fun getChannel(
        channelId: Long
    ): ChannelResponse {

        val adminChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        return adminChannel.toResponse()
    }


    fun getAllChannels(): List<ChannelResponse> =

        channelRepository.findAll().map { it.toResponse() }


    fun createChannel(
        request: CreateChannelRequest
    ): ChannelResponse {

        val createChannel = AdminChannel(
            title = request.title,
            description = request.description,
            gameTitle = request.gameTitle,
            ageLimit = request.ageLimit,
        )
        return channelRepository.save(createChannel).toResponse()
    }


    fun updateChannel(
        channelId: Long,
        request: UpdateChannelRequest
    ): ChannelResponse {

        val updateChannel =
            channelRepository.findByIdOrNull(channelId)
                ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        val (title, description, gameTitle, ageLimit) = request

        updateChannel.title = title
        updateChannel.description = description
        updateChannel.gameTitle = gameTitle
        updateChannel.ageLimit = ageLimit

        return channelRepository.save(updateChannel).toResponse()
    }


    fun deleteChannel(
        channelId: Long
    ) {

        channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("")
        channelRepository.deleteById(channelId)
    }


    fun warnChannel(
        channelId: Long,
        request: WarnChannelRequest
    ): String {

        channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        val warning = Warning(
            reason = request.reason,
            source = request.source,
            targetId = channelId,
            type = Type.CHANNEL
        )
        warningRepository.save(warning)
        return request.source
    }


    fun activateChannel(
        channelId: Long
    ) {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        if (!channel.active) {
            channel.active = true
            channelRepository.save(channel)
        }
    }


    fun deactivateChannel(
        channelId: Long
    ) {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        if (channel.active) {
            channel.active = false
            channelRepository.save(channel)
        }
    }


    fun assignManager(
        channelId: Long,
        userId: Long
    ): String {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")

        val user = memberRepository.findByIdOrNull(userId)
            ?: throw EntityNotFoundException("유저 찾을 수 없습니다")

        val channelManager = ChannelManager(user, channel)
        channelManagerRepository.save(channelManager)
        return "Manager assigned successfully"
    }

}

