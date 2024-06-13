package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.WarnChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model.AdminChannel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model.Type
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model.Warning
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository.WarningRepository

@Service
class ChannelServiceImpl(
    private val channelRepository: ChannelRepository,
    private val warningRepository: WarningRepository
): ChannelService {


    override fun getChannel(channelId: Long): ChannelResponse {
        val adminChannel = channelRepository.findByIdOrNull(channelId)?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        return adminChannel.toResponse()
    }

    override fun getAllChannels(): List<ChannelResponse> {
        return channelRepository.findAll().map { it.toResponse() }
    }

    override fun createChannel(request: CreateChannelRequest): ChannelResponse {
       val createChannel = AdminChannel(
           title = request.title,
           description = request.description,
           gameTheme = request.gameTheme,
           ageLimit = request.ageLimit,
       )
        return channelRepository.save(createChannel).toResponse()
    }

    override fun updateChannel(channelId: Long, request: UpdateChannelRequest): ChannelResponse {
        val updateChannel = channelRepository.findByIdOrNull(channelId) ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        val (title, description,gameTheme,ageLimit) = request

        updateChannel.title = title
        updateChannel.description = description
        updateChannel.gameTheme = gameTheme
        updateChannel.ageLimit = ageLimit

        return channelRepository.save(updateChannel).toResponse()
    }

    override fun deleteChannel(channelId: Long) {
        channelRepository.findByIdOrNull(channelId) ?: throw EntityNotFoundException("")
        channelRepository.deleteById(channelId)
    }

    override fun warnChannel(channelId: Long, request: WarnChannelRequest) {
        warningRepository.findByIdOrNull(channelId) ?:  throw EntityNotFoundException("채널을 찾을 수 없습니다")
        val warning = Warning(
            reason = request.reason,
            source = request.source,
            targetId = request.targetId,
            type = Type.CHANNEL
            )
       warningRepository.save(warning)
    }

    override fun activateChannel(channelId: Long) {
        val channel = channelRepository.findByIdOrNull(channelId) ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        if (!channel.active) {
            channel.active = true
            channelRepository.save(channel)
        }
    }

    override fun deactivateChannel(channelId: Long) {
        val channel = channelRepository.findByIdOrNull(channelId) ?: throw EntityNotFoundException("채널을 찾을 수 없습니다")
        if (channel.active) {
            channel.active = false
            channelRepository.save(channel)
        }
    }

}

