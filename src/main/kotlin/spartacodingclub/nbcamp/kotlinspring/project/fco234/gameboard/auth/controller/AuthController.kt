package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service.AuthService

@RequestMapping("/api/v1/auth")
@RestController
class AuthController (

    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<String> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.login(request))


    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<String> {

        val userResponse = authService.signUp(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("해당 이메일로 코드를 보냈으니 해당 코드를 verify-email에 입력해주세요")
    }


    @PostMapping("/verify-email")
    fun verifyEmail(
        @RequestParam email: String,
        @RequestParam code: String
    ): ResponseEntity<String> =

        authService.verifyEmail(email, code).let {
            ResponseEntity
                .status(if (it) HttpStatus.OK else HttpStatus.BAD_REQUEST)
                .body("이메일 인증 ${if (it) "성공" else "실패"}")
        }

}