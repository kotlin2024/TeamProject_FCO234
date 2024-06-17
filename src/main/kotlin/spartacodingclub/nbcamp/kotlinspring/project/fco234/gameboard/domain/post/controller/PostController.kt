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
    //    @PreAuthorize("hasRole('PLATFORM_USER')") // 추후에 플랫폼 유저가 아니라 채널 유저가 가능하게 해야함 또한 상위계층으로 채털 유저의 권한만 가능하게 해도 ADMIN이나 플랫폼 유저 또한 접근이 가능해야함 채널<플랫폼<ADMIN
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


//    @PreAuthorize("hasRole('PLATFORM_USER')")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(channelId, postId, request))

//    @PreAuthorize("hasRole('PLATFORM_USER')")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable channelId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(channelId, postId))

}

