package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.service


import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdatePasswordRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdateProfileRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberPasswordLogRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundExceptionNew
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class MemberService (

    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val memberPasswordLogRepository: MemberPasswordLogRepository
) {

    fun getUserProfile(
        memberId: Long
    ): MemberResponse {

        val member = memberRepository.findByIdOrNull(memberId)
            ?:throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")
        return MemberResponse.from(member)
    }


    fun getMyProfile(): MemberResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val member = memberRepository.findByIdOrNull(principal.id)
            ?: throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")

        return MemberResponse.from(member)
    }


    fun updateProfile(
        request: UpdateProfileRequest
    ): MemberResponse {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal
        val targetMember = memberRepository.findByIdOrNull(principal.id)
            ?: throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")

        targetMember.updateProfile(request)

        return MemberResponse.from(memberRepository.save(targetMember))
    }


    @Transactional
    fun updatePassword(
        request: UpdatePasswordRequest
    ){

        val authentication= SecurityContextHolder.getContext().authentication
        val principal= authentication.principal as UserPrincipal
        val targetMember = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")


        val passwordLog = memberPasswordLogRepository.findByIdOrNull(targetMember!!.id)
            ?: throw ModelNotFoundExceptionNew("Member")

        if (passwordLog.passwords.any { passwordEncoder.matches(request.updatePassword, it) })
            throw IllegalArgumentException("사용한 마지막 3개 비밀번호는 다시 사용할 수 없습니다.")

        passwordEncoder.encode(request.updatePassword).let {
            passwordLog.add(it)
            targetMember.password = it
        }

        memberPasswordLogRepository.save(passwordLog)
        memberRepository.save(targetMember)

//        if(passwordEncoder.matches(request.updatePassword,member!!.password)) throw RuntimeException("변경하려는 비밀번호와 현재 비밀번호가 똑같음!!!")
//        // 최대 3번 이내의 비밀번호를 저장하는 로직이 필요함
//        val recentPasswords = checkingPasswordRepository.findTop3ByEmailOrderByIdDesc(member.email)
//        if (recentPasswords.any { checkingPassword: CheckingPassword -> passwordEncoder.matches(request.updatePassword, checkingPassword.oldPassword) }) {
//            throw RuntimeException("최근 3번 이내에 사용한 비밀번호는 사용할 수 없습니다.")
//        }
//
//        member.password = passwordEncoder.encode(request.updatePassword)
//        memberRepository.save(member)
//
//        if (recentPasswords.size >= 3) {
//            val oldestPasswordId = recentPasswords.minByOrNull { it.id!! }!!.id!!// 가장 오래된 비밀번호 찾기
//
//            checkingPasswordRepository.deleteByEmailAndIdLessThanEqualOrderByOldPasswordAsc(member.email, oldestPasswordId)
//        }
//
//
//        val checkingPassword = CheckingPassword(
//            email = member.email,
//            oldPassword = member.password)
//        checkingPasswordRepository.save(checkingPassword)

    }

}