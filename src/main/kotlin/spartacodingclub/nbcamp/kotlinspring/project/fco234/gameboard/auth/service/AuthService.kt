package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.dto.UserResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt.JwtPlugin
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.Profile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.UserRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.User
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.repository.UserRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.emailAuthentication.service.EmailService
import java.time.Duration
import java.util.*


@Service
class AuthService(
    private val jwtPlugin: JwtPlugin,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,

)
{

    private val validationCodes: MutableMap<String,String> = mutableMapOf()

    fun login(loginRequest: LoginRequest): String? {

        val user= userRepository.findByEmail(loginRequest.email) ?: throw RuntimeException("요거 따로 만들어서 관리해야함")
        if(validationCodes.containsKey(user.email)) throw RuntimeException("이메일 인증이 되지 않았습니다")

        if(!passwordEncoder.matches(loginRequest.password,user.password)){
            throw RuntimeException("요거도 따로 만들어야함")
        }

        return jwtPlugin.generateToken(subject = user.id.toString(),role=user.role, birthday = user.profile.birthday, expirationPeriod = Duration.ofHours(1), email = user.email)
    }


    fun signUp(signUpRequest: SignUpRequest): UserResponse? {

        if(userRepository.existsByEmail(signUpRequest.email)){
            throw IllegalArgumentException("ID is already exists")
        }

        val verificationCode = UUID.randomUUID().toString().substring(0, 6)
        val user = User(
            email = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password),
            profile = Profile(
                name = signUpRequest.name,
                birthday = signUpRequest.birthday
            ),
            role = UserRole.PLATFORM_USER,
            //emailVerificationCode = verificationCode
        )

        validationCodes.put(key= user.email, value = verificationCode)
        sendAuthenticationEmail(user.email, verificationCode)
        return userRepository.save(user).toResponse()
    }

    private fun sendAuthenticationEmail(email: String, code: String) {
        val subject = "FC345의 프로젝트 서비스 회원가입 코드입니다"
        val text = "인증 코드: $code"
        emailService.sendEmail(email, subject, text)
    }


    fun verifyEmail(email: String, code: String): Boolean {
        if(!validationCodes.containsKey(key = email)) throw RuntimeException("해당 이메일로 진행된 인증 기록이 없음")
        else{
            return if (validationCodes[email]!! == code){
                validationCodes.remove(email)
                true
            }
            else false
        }
    }



    fun getUserByEmail(userEmail: String): User{

        val usersEmail =userRepository.findByEmail(userEmail) ?: throw IllegalArgumentException("해당 이메일이 없음")
        return usersEmail
    }

}