package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service.ChannelService

@RestController
@RequestMapping("/api/v1/channels")
class ChannelController (

    private val channelService: ChannelService
) {

    @GetMapping
    fun getAllChannels(): ResponseEntity<List<ChannelResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getAllChannels())


    @GetMapping("/{channelId}")
    fun getChannel(
        @PathVariable channelId: Long,
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getChannel(channelId))


    @PostMapping
    fun createChannel(
        @RequestBody request: CreateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelService.createChannel(request))

}