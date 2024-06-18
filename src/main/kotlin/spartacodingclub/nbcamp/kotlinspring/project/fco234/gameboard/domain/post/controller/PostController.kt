package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service.PostService

@RestController
@RequestMapping("/api/v1/channels/{channelId}/posts")
class PostController(

    private val postService: PostService,
) {
    @PostMapping
    fun createPost(
        @PathVariable channelId: Long,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(channelId, request))


    @GetMapping
    fun getAllPosts(
        @PathVariable channelId: Long
    ): ResponseEntity<List<PostResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPostsWithinChannel(channelId))


    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(channelId, postId))



    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(channelId, postId, request))

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(channelId, postId))

}

