package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.service.ChannelService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.warning.dto.response.WarningResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.dto.response.ChannelMemberPositionResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.service.ChannelMemberPositionService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.service.CommentService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service.PostService

@RestController
@RequestMapping("/api/v1/platform-admin")
class PlatformAdminController (

    private val channelMemberPositionService: ChannelMemberPositionService,
    private val channelService: ChannelService,
    private val postService: PostService,
    private val commentService: CommentService
) {

//    @GetMapping("/members")
//    fun getAllMembers(): ResponseEntity<List<MemberResponse>> = TODO()
//
//    @GetMapping("/members/{memberId}")
//    fun getMember(
//        @PathVariable memberId: Long
//    ): ResponseEntity<MemberDetailedResponse> = TODO()
//
//    @PostMapping("/members/{memberId}/warning")
//    fun warnMember(
//        @PathVariable memberId: Long
//    ): ResponseEntity<WarningResponse> = TODO()
//
//    @PatchMapping("/members/{memberId}/block")
//    fun blockMember(
//        @PathVariable memberId: Long,
//        @RequestParam block: Boolean
//    ): ResponseEntity<Unit> = TODO()
//
//    @DeleteMapping("/members/{memberId}")
//    fun deleteMember(
//        @PathVariable memberId: Long,
////        @RequestParam(name = "db_erasing") dbErasing: Boolean
//    ): ResponseEntity<Unit> = TODO()
//
//
//    @PatchMapping("/members/{memberId}/posts/{postId}/block")
//    fun blockPost(
//        @PathVariable memberId: Long,
//        @PathVariable postId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @PatchMapping("/members/{memberId}/posts/{postId}/unblock")
//    fun unblockPost(
//        @PathVariable memberId: Long,
//        @PathVariable postId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @DeleteMapping("/members/{memberId}/posts/{postId}")
//    fun deletePost(
//        @PathVariable memberId: Long,
//        @PathVariable postId: Long,
////        @RequestParam(name = "db_erasing") dbErasing: Boolean
//    ): ResponseEntity<Unit> = TODO()
//
//
//
//    @PatchMapping("/members/{memberId}/comments/{commentId}/block")
//    fun blockComment(
//        @PathVariable memberId: Long,
//        @PathVariable commentId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @PatchMapping("/members/{memberId}/comments/{commentId}/unblock")
//    fun unblockComment(
//        @PathVariable memberId: Long,
//        @PathVariable commentId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @DeleteMapping("/members/{memberId}/comments/{commentId}")
//    fun deleteComment(
//        @PathVariable memberId: Long,
//        @PathVariable commentId: Long,
////        @RequestParam(name = "db_erasing") dbErasing: Boolean
//    ): ResponseEntity<Unit> = TODO()
//
//
//    @GetMapping("/channels/{channelId}/members")
//    fun getAllMembersInChannel(
//        @PathVariable channelId: Long
//    ): ResponseEntity<List<MemberResponse>> = TODO()
//
//    @PatchMapping("/channels/{channelId}/activate")
//    fun activateChannel(
//        @PathVariable channelId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @PatchMapping("/channels/{channelId}/deactivate")
//    fun deactivateChannel(
//        @PathVariable channelId: Long
//    ): ResponseEntity<Unit> = TODO()
//
//    @DeleteMapping("/channels/{channelId}")
//    fun deleteChannel(
//        @PathVariable channelId: Long,
////        @RequestParam(name = "db_erasing") dbErasing: Boolean
//    ): ResponseEntity<Unit> = TODO()
//
//    @PutMapping("/channels/{channelId}/members/{memberId}")
//    fun reassignMember(
//        @PathVariable channelId: Long,
//        @PathVariable memberId: Long,
//        @RequestParam position: MemberPosition
//    ): ResponseEntity<ChannelMemberPositionResponse> = TODO()
//
//    @DeleteMapping("/channels/{channelId}/members/{memberId}")
//    fun deleteMemberFromChannel(
//        @PathVariable channelId: Long,
//        @PathVariable memberId: Long,
//    ): ResponseEntity<Unit> = TODO()
//
}