package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundExceptionNew
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.UnauthorizedAccessException

@Service
class PostService (

    private val postRepository: PostRepository,
    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository
) {

    fun createPost(
        channelId: Long,
        createPostRequest: CreatePostRequest
    ): PostResponse {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundExceptionNew("Channel")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("너 누구야")

        val createdPost = Post(
            title = createPostRequest.title,
            content = createPostRequest.content,
            author = member,
            channel = channel
        )

        return PostResponse.from(
            postRepository.save(createdPost)
        )
    }

    fun getAllPostsWithinChannel(
        channelId: Long
    ): List<PostResponse> =

        postRepository.findAllByChannelId(channelId)
            .map{ PostResponse.from(it) }


    fun getPost(
        channelId: Long,
        postId: Long
    ): PostResponse =

        PostResponse.from(
            postRepository.findByChannelIdAndId(channelId, postId)
                ?: throw ModelNotFoundExceptionNew("Post")
        )


    fun updatePost(
        channelId: Long,
        postId: Long,
        request: UpdatePostRequest
    ): PostResponse {

        val targetPost = postRepository.findByChannelIdAndId(channelId, postId)
            ?: throw ModelNotFoundExceptionNew("Post")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundExceptionNew("Member")

        if (targetPost.author.id != member.id && (member.role == MemberRole.MEMBER || (member.role == MemberRole.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        targetPost.update(request)

        return PostResponse.from(postRepository.save(targetPost))
    }


    fun deletePost(
        channelId: Long,
        postId: Long
    ) {

        val targetPost = postRepository.findByChannelIdAndId(channelId, postId)
            ?: throw ModelNotFoundExceptionNew("Post")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundExceptionNew("Member")

        if(targetPost.author.id != member.id && (member.role == MemberRole.MEMBER || (member.role == MemberRole.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        commentRepository.deleteAll(commentRepository.findAllByPostId(postId))
        postRepository.delete(targetPost)
    }

}