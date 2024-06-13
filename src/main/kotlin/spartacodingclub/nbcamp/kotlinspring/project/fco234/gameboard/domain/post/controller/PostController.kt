package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.UpdatePostRequest

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service.PostService

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @GetMapping()
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPosts())
    }


    @PostMapping("/")
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(request))
    }

    @PutMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long, @RequestBody request: UpdatePostRequest): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, request))
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Unit> {
        postService.deletePost(id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}

