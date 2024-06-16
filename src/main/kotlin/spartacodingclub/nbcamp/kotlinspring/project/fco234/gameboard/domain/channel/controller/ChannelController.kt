package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request.WarnChannelRequest

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin/channels")
class ChannelController (

    private val channelService: ChannelService
) {

    @GetMapping("/{channelid}")
    fun getChannel(
        @PathVariable channelid: Long,
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getChannel(channelid))


    @GetMapping
    fun getAllChannels():
            ResponseEntity<List<ChannelResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getAllChannels())



    @PostMapping
    fun createChannel(
        @RequestBody request: CreateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelService.createChannel(request))



    @PutMapping("/{channelid}")
    fun updateChannel(
        @PathVariable channelid: Long,
        @RequestBody request: UpdateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.updateChannel(channelid, request))



    @DeleteMapping("/{channelid}")
    fun deleteChannel(
        @PathVariable channelid: Long
    ): ResponseEntity<Unit> {

        channelService.deleteChannel(channelid)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


    @PostMapping("/{channelId}/warn")
    fun warnChannel(
        @PathVariable channelId: Long,
        @RequestBody request: WarnChannelRequest
    ): ResponseEntity<String> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.warnChannel(channelId,request))


    @PatchMapping("/{channelId}/activate")
    fun activateChannel(
        @PathVariable channelId: Long
    ): ResponseEntity<Unit> {

        channelService.activateChannel(channelId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }


    @PatchMapping("/{channelId}/deactivate")
    fun deactivateChannel(
        @PathVariable channelId: Long
    ): ResponseEntity<Unit> {

        channelService.deactivateChannel(channelId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }



    @PostMapping("/{channelId}/assign-manager/{userId}")
    fun assignManager(
        @PathVariable channelId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<String> {

        channelService.assignManager(channelId, userId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Manager assigned successfully")
    }

}