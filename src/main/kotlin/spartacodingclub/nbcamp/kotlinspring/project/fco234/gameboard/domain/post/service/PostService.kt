package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.UpdatePostRequest


interface PostService {
    fun getPost(id: Long): PostResponse
    fun getAllPosts(): List<PostResponse>
    fun createPost(createPostRequest: CreatePostRequest): PostResponse
    fun updatePost(id: Long, updatePostRequest: UpdatePostRequest): PostResponse
    fun deletePost(id: Long)

    }

