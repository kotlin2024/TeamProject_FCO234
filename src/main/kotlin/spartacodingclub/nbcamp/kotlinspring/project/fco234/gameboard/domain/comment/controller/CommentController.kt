package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.response.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.service.CommentService

@RestController
@RequestMapping("/api/v1/channels/{channelId}/posts/{postId}/comments")
class CommentController (
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(channelId, postId, request))


    @GetMapping
    fun getCommentsWithinPost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<List<CommentResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentsWithinPost(channelId, postId))


    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(channelId, postId, commentId, request))


    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(channelId, postId, commentId))

}