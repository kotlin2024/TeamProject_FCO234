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
import java.time.Duration


@Service
class imsiSignupService(
    private val jwtPlugin: JwtPlugin,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,)
{
    fun login(loginRequest: LoginRequest): String? {

        val user= userRepository.findByEmail(loginRequest.email) ?: throw RuntimeException("요거 따로 만들어서 관리해야함")

        if(!passwordEncoder.matches(loginRequest.password,user.password)){
            throw RuntimeException("요거도 따로 만들어야함")
        }

        return jwtPlugin.generateToken(subject = user.id.toString(),role=user.role, birthday = user.profile.birthday, expirationPeriod = Duration.ofHours(1), email = user.email)
    }

    fun signUp(signUpRequest: SignUpRequest): UserResponse? {

        if(userRepository.existsByEmail(signUpRequest.email)){
            throw IllegalArgumentException("ID is already exists")
        }

        return userRepository.save(
            User(
                email=signUpRequest.email,
                password=passwordEncoder.encode(signUpRequest.password),
                profile= Profile(
                    name=signUpRequest.name,
                    birthday = signUpRequest.birthday
                ),
                role= UserRole.PLATFORM_USER
            )
        ).toResponse()
    }
}