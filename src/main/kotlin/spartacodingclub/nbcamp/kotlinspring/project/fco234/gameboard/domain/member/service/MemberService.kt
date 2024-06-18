package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.service


import org.hibernate.annotations.Comments
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdatePasswordRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdateProfileRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberPasswordLogRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class MemberService (

    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val memberPasswordLogRepository: MemberPasswordLogRepository,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {

    fun getUserProfile(
        memberId: Long
    ): MemberResponse {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member")
        return MemberResponse.from(member)
    }


    fun getMyProfile(): MemberResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw ModelNotFoundException("Member")

        return MemberResponse.from(member)
    }


    fun updateProfile(
        request: UpdateProfileRequest
    ): MemberResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val targetMember = memberRepository.findByIdOrNull(principal.id)
            ?: throw ModelNotFoundException("Member")

        targetMember.updateProfile(request)

        return MemberResponse.from(
            memberRepository.save(targetMember)
        )
    }


    @Transactional
    fun updatePassword(
        request: UpdatePasswordRequest
    ){

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val targetMember = memberRepository.findByEmail(principal.email)
            ?: throw ModelNotFoundException("Member")
        val passwordLog = memberPasswordLogRepository.findByIdOrNull(targetMember!!.id)
            ?: throw ModelNotFoundException("MemberPasswordLog")

        if (passwordLog.passwords.any { passwordEncoder.matches(request.updatePassword, it) })
            throw IllegalArgumentException("사용한 마지막 3개 비밀번호는 다시 사용할 수 없습니다.")

        passwordEncoder.encode(request.updatePassword).let {
            passwordLog.add(it)
            targetMember.password = it
        }

        memberPasswordLogRepository.save(passwordLog)
        memberRepository.save(targetMember)


    }

    fun blockMemberInService(
        memberId: Long
    ) {

        val targetMember = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member")

        targetMember.role = MemberPosition.BLACKLIST

        memberRepository.save(targetMember)
    }

    fun unblockMemberInService(
        memberId: Long
    ) {

        val targetMember = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member")

        targetMember.role = MemberPosition.MEMBER

        memberRepository.save(targetMember)
    }

    @Transactional
    fun deleteMember(
        memberId: Long
    ) {

        val targetMember = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member")

        val posts = postRepository.findAllByAuthor(targetMember)
        val comments = mutableListOf<Comment>()
        for (each in posts)
            comments += commentRepository.findAllByPostId(each.id!!)

        commentRepository.deleteAll(comments)
        postRepository.deleteAll(posts)

        memberRepository.delete(targetMember)
    }

}