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
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundExceptionNew
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
                    ?: throw ModelNotFoundExceptionNew("Channel"),
                postId)?.id
                ?: throw ModelNotFoundExceptionNew("Post"))
            .map{ CommentResponse.from(it) }


    @Transactional
    fun createComment(
        channelId: Long,
        postId: Long,
        createCommentRequest: CreateCommentRequest
    ): CommentResponse {

        val post = postRepository.findByChannelIdAndId(
            channelRepository.findByIdOrNull(channelId)?.id
                ?: throw ModelNotFoundExceptionNew("Channel"),
            postId)
            ?: throw ModelNotFoundExceptionNew("Post")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundExceptionNew("Member")

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

        val targetComment = commentRepository.findByPostIdAndId(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundExceptionNew("Channel"),
                postId)?.id
                ?: throw ModelNotFoundExceptionNew("Post"),
            commentId
        ) ?: throw ModelNotFoundExceptionNew("Comment")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundExceptionNew("Member")

        if(targetComment.author.id != member.id  && (member.role == MemberRole.MEMBER ||(member.role== MemberRole.CHANNEL_MEMBER)))
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

        val targetComment = commentRepository.findByPostIdAndId(
            postRepository.findByChannelIdAndId(
                channelRepository.findByIdOrNull(channelId)?.id
                    ?: throw ModelNotFoundExceptionNew("Channel"),
                postId)?.id
                ?: throw ModelNotFoundExceptionNew("Post"),
            commentId
        ) ?: throw ModelNotFoundExceptionNew("Comment")


        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundExceptionNew("Member")


        if(targetComment.post.author.id != member.id && (member.role == MemberRole.MEMBER || (member.role == MemberRole.CHANNEL_MEMBER)))
            throw UnauthorizedAccessException()

        commentRepository.delete(targetComment)
    }

}