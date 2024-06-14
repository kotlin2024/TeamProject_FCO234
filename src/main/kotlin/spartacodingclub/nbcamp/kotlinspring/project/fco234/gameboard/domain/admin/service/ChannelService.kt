package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.service

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.WarnChannelRequest

interface ChannelService {
    fun getChannel(channelId: Long): ChannelResponse
    fun getAllChannels(): List<ChannelResponse>
    fun createChannel(request: CreateChannelRequest): ChannelResponse
    fun updateChannel(channelId: Long, request: UpdateChannelRequest): ChannelResponse
    fun deleteChannel(channelId: Long)
    fun warnChannel(channelId: Long, request: WarnChannelRequest):String
    fun activateChannel(channelId: Long)
    fun deactivateChannel(channelId: Long)

}
