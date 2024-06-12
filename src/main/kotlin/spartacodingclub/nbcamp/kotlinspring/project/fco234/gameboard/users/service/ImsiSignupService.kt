package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.UserResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt.JwtPlugin
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model.Profile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model.UserRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model.Users
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.repository.UserRepository
import java.time.Duration
import java.util.*

@Service
class imsiSignupService(
    private val jwtPlugin: JwtPlugin,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,)
{
    fun login(loginRequest: LoginRequest): String? {

        val user= userRepository.findByLoginId(loginRequest.loginId) ?: throw RuntimeException("요거 따로 만들어서 관리해야함")

        if(!passwordEncoder.matches(loginRequest.loginPassword,user.loginPassword)){
            throw RuntimeException("요거도 따로 만들어야함")
        }

        return jwtPlugin.generateToken(subject = user.id.toString(),role=user.role, birthday = user.profile.birthday, expirationPeriod = Duration.ofHours(1) )
    }

    fun signUp(signUpRequest: SignUpRequest): UserResponse? {

        if(userRepository.existsByLoginId(signUpRequest.loginId)){
            throw IllegalArgumentException("ID is already exists")
        }

        return userRepository.save(
            Users(
                loginId=signUpRequest.loginId,
                loginPassword=passwordEncoder.encode(signUpRequest.loginPassword),
                profile= Profile(
                    loginNickname=signUpRequest.loginNickname,
                    birthday = signUpRequest.birthday
                ),
                role= UserRole.PLATFORM_USER
            )
        ).toResponse()
    }
}