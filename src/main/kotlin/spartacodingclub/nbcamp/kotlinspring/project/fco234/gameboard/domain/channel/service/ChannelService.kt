package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request.WarnChannelRequest

interface ChannelService {
    fun getChannel(channelId: Long): ChannelResponse
    fun getAllChannels(): List<ChannelResponse>
    fun createChannel(request: CreateChannelRequest): ChannelResponse
    fun updateChannel(channelId: Long, request: UpdateChannelRequest): ChannelResponse
    fun deleteChannel(channelId: Long)
    fun warnChannel(channelId: Long, request: WarnChannelRequest):String
    fun activateChannel(channelId: Long)
    fun deactivateChannel(channelId: Long)
    fun assignManager(channelId: Long, userId:Long):String

}
