package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.dto.response.ChannelMemberPositionResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service.ChannelService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.service.ChannelMemberPositionService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.service.CommentService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service.PostService

@RestController
@RequestMapping("/api/v1/channel-admmin/channels/{channelId}")
class ChannelAdminController (

    private val channelMemberPositionService: ChannelMemberPositionService,
    private val channelService: ChannelService,
    private val postService: PostService,
    private val commentService: CommentService
) {

    @GetMapping("/members")
    fun getChannelMembers(
        @PathVariable channelId: Long
    ): ResponseEntity<List<MemberResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelMemberPositionService.getChannelMembers(channelId))


    @PutMapping("/assign-manager")
    fun assignChannelmanager(
        @PathVariable channelId: Long,
        @RequestParam(name = "member_id") memberId: Long
    ): ResponseEntity<ChannelMemberPositionResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelMemberPositionService.assignChannelManager(channelId, memberId))


    @PutMapping("/accept-member")
    fun acceptMember(
        @PathVariable channelId: Long,
        @RequestParam(name = "member_id") memberId: Long
    ): ResponseEntity<ChannelMemberPositionResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelMemberPositionService.acceptMember(channelId, memberId))


    @PutMapping("/block-member")
    fun blockMember(
        @PathVariable channelId: Long,
        @RequestParam(name = "member_id") memberId: Long
    ): ResponseEntity<ChannelMemberPositionResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelMemberPositionService.blockMember(channelId, memberId))

    @PutMapping("/unblock-member")
    fun unblockMember(
        @PathVariable channelId: Long,
        @RequestParam(name = "member_id") memberId: Long
    ): ResponseEntity<ChannelMemberPositionResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelMemberPositionService.acceptMember(channelId, memberId))


    @DeleteMapping("/posts/{postId}")
    fun deletePost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(channelId, postId))


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(channelId, postId, commentId))


    @PutMapping
    fun updateChannelProfile(
        @PathVariable channelId: Long,
        @RequestBody request: UpdateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.updateChannel(channelId, request))


    @DeleteMapping
    fun deleteChannel(
        @PathVariable channelId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(channelService.deleteChannel(channelId))

}