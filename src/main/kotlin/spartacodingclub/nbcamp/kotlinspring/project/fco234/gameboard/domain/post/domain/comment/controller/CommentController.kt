package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.controller
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.service.CommentService

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping
    fun getComment(
       @PathVariable postId: Long,
    ): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getComment(postId))
    }

    @PostMapping()
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody @Valid request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, request))
    }

        @DeleteMapping("/{commentId}")
        fun deleteComment(
            @PathVariable postId: Long,
            @PathVariable commentId: Long
        ): ResponseEntity<Unit> {
            commentService.deleteComment(postId, commentId,)
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build()
        }


}