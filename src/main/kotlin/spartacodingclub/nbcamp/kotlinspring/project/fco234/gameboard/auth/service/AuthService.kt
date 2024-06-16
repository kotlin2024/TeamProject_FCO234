package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt.JwtPlugin
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberProfile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPasswordLog
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberPasswordLogRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.emailsender.service.EmailSenderService
import java.time.Duration
import java.util.*

@Service
class AuthService (

    private val jwtPlugin: JwtPlugin,
    private val memberRepository: MemberRepository,
    private val memberPasswordLogRepository: MemberPasswordLogRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailSenderService: EmailSenderService,
) {

    private val validationCodes: MutableMap<String,String> = mutableMapOf()



    fun login(
        request: LoginRequest
    ): String? {

        val member = memberRepository.findByUsername(request.username)
            ?: throw ModelNotFoundExceptionNew("Member")

        if (!passwordEncoder.matches(request.password, member.password))
            throw IncorrectPasswordException()
        if (validationCodes.containsKey(member.email))
            throw MemberNotVerifiedException()

        return jwtPlugin.generateToken(
            subject = member.id.toString(),
            role = member.role,
            birthday = member.profile.birthday,
            expirationPeriod = Duration.ofHours(1),
            email = member.email
        )
    }


    @Transactional
    fun signUp(
        request: SignUpRequest
    ): MemberResponse? {

        if(memberRepository.existsByEmail(request.email))
            throw EmailAlreadyOccupiedException()
        if(memberRepository.existsByUsername(request.username))
            throw UsernameAlreadyOccupiedException()

        val member = Member(
            email = request.email,
            username = request.username,
            password = passwordEncoder.encode(request.password),
            profile = MemberProfile(
                nickname = request.nickname,
                birthday = request.birthday,
                introduction = ""
            ),
            role = MemberRole.MEMBER,
        )
        val memberPasswordLog = MemberPasswordLog(
            member = member
        )

        sendVerificationEmail(member.email)
        memberPasswordLogRepository.save(memberPasswordLog)

        return MemberResponse.from(memberRepository.save(member))
    }


    private fun sendVerificationEmail(email: String) {

        val verificationCode = UUID.randomUUID().toString().substring(0, 6)
        validationCodes.put(email, verificationCode)

        val subject = "FC345의 프로젝트 서비스 회원가입 코드입니다"
        val text = "인증 코드: $verificationCode"

        emailSenderService.sendEmail(email, subject, text)
    }


    fun verifyEmail(email: String, code: String): Boolean =

        if (!memberRepository.existsByEmail(email))
            throw ModelNotFoundExceptionNew("Member")
        else if(!validationCodes.containsKey(email))
            throw IllegalArgumentException("해당 사용자는 이미 인증되었습니다.")
        else ((validationCodes.remove(email) ?: "") == code)

}