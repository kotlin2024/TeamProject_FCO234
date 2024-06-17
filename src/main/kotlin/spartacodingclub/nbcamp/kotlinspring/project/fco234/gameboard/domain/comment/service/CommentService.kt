package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.service

import org.springframework.data.repository.findByIdOrNull
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.response.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.UnauthorizedAccessException

@Service
class CommentService (

    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {

    fun getCommentsWithinPost(
        channelId: Long,
        postId: Long
    ): List<CommentResponse> =

        commentRepository.findAllByPostId(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundException("Channel"),
                postId)?.id
                ?: throw ModelNotFoundException("Post"))
            .map{ CommentResponse.from(it) }


    @Transactional
    fun createComment(
        channelId: Long,
        postId: Long,
        createCommentRequest: CreateCommentRequest
    ): CommentResponse {

        val post = postRepository.findByChannelIdAndId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundException("Channel"),
            postId)
            ?: throw ModelNotFoundException("Post")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        return CommentResponse.from(
            commentRepository.save(
                Comment(
                    content = createCommentRequest.content,
                    post = post,
                    author = member,
                    )
            )
        )
    }


    fun updateComment(
        channelId: Long,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val targetComment = commentRepository.findByPostIdAndId(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundException("Channel"),
                postId)?.id
                ?: throw ModelNotFoundException("Post"),
            commentId
        ) ?: throw ModelNotFoundException("Comment")

        if(targetComment.author.id != member.id  && (member.role == MemberPosition.MEMBER ||(member.role== MemberPosition.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        targetComment.update(request)

        return CommentResponse.from(
            commentRepository.save(targetComment)
        )
    }

    @Transactional
    fun deleteComment(
        channelId: Long,
        postId: Long,
        commentId: Long
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")

        val targetComment = commentRepository.findByPostIdAndId(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundException("Channel"),
                postId)?.id
                ?: throw ModelNotFoundException("Post"),
            commentId
        ) ?: throw ModelNotFoundException("Comment")

        if(targetComment.post.author.id != member.id && (member.role == MemberPosition.MEMBER || (member.role == MemberPosition.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        commentRepository.delete(targetComment)
    }

}