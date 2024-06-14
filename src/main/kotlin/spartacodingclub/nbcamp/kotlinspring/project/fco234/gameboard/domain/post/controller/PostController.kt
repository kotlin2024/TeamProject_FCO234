package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service.AuthService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.UpdatePostRequest

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service.PostService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
    private val authService: AuthService,
) {

    @GetMapping("/get/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @GetMapping("/get")
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPosts())
    }


    @PreAuthorize("hasRole('PLATFORM_USER')") // 추후에 플랫폼 유저가 아니라 채널 유저가 가능하게 해야함 또한 상위계층으로 채털 유저의 권한만 가능하게 해도 ADMIN이나 플랫폼 유저 또한 접근이 가능해야함 채널<플랫폼<ADMIN
    @PostMapping("/")
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<PostResponse> {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(request))
    }

    @PreAuthorize("hasRole('PLATFORM_USER')")
    @PutMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long, @RequestBody request: UpdatePostRequest): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, request))
    }

    @PreAuthorize("hasRole('PLATFORM_USER')")
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Unit> {
        postService.deletePost(id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}

