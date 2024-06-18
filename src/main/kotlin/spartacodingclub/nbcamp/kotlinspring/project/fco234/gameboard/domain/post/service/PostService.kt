package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.repository.ChannelMemberPositionRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.UnauthorizedAccessException

@Service
class PostService (

    private val postRepository: PostRepository,
    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
    private val channelMemberPositionRepository: ChannelMemberPositionRepository
) {

    fun createPost(
        channelId: Long,
        request: CreatePostRequest
    ): PostResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel")

        if (!channelMemberPositionRepository.existsByChannelIdAndMemberId(channelId, principal.id))
            throw UnauthorizedAccessException()

        return PostResponse.from(
            postRepository.save(
                Post(
                    title = request.title,
                    content = request.content,
                    author = member,
                    channel = channel
                )
            )
        )
    }


    fun getAllPostsWithinChannel(
        channelId: Long
    ): List<PostResponse> =

        postRepository.findAllByChannelId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel")
        ).map{ PostResponse.from(it) }


    fun getPost(
        channelId: Long,
        postId: Long
    ): PostResponse =

        PostResponse.from(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundException("Channel"),
                postRepository.findByIdOrNull(postId)?.id
                    ?: throw ModelNotFoundException("Post")
            ) ?: throw ModelNotFoundException("Post")
        )


    fun updatePost(
        channelId: Long,
        postId: Long,
        request: UpdatePostRequest
    ): PostResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val targetPost = postRepository.findByChannelIdAndId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            postRepository.findByIdOrNull(postId)?.id
                ?: throw ModelNotFoundException("Post")
        ) ?: throw ModelNotFoundException("Post")

        if (targetPost.author.id != member.id && (member.role == MemberPosition.MEMBER || (member.role == MemberPosition.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        targetPost.updateFrom(request)

        return PostResponse.from(
            postRepository.save(targetPost)
        )
    }


    @Transactional
    fun deletePost(
        channelId: Long,
        postId: Long
    ) {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val targetPost = postRepository.findByChannelIdAndId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            postRepository.findByIdOrNull(postId)?.id
                ?: throw ModelNotFoundException("Post")
        ) ?: throw ModelNotFoundException("Post")

        val relatedComments = commentRepository.findAllByPostId(postId)

        if(targetPost.author.id != member.id && (member.role == MemberPosition.MEMBER || (member.role == MemberPosition.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        commentRepository.deleteAll(relatedComments)
        postRepository.delete(targetPost)
    }

}