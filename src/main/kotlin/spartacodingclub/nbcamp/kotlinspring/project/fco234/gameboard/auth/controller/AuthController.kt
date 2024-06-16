package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request.UpdatePasswordRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.service.AuthService

@RestController
class AuthController (
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<String> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.login(loginRequest))


    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<String> {

        val userResponse = authService.signUp(signUpRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("해당 이메일로 코드를 보냈으니 해당 코드를 verify-email에 입력해주세요")
    }


    @PostMapping("/verify-email")
    fun verifyEmail(
        @RequestParam email: String,
        @RequestParam code: String
    ): ResponseEntity<String> =

        if (authService.verifyEmail(email, code)) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body("이메일 인증 완료")
        } else {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("인증 코드가 유효하지 않습니다.")
        }



    @PutMapping("/api/users/update-password")
    fun updatePassword(
        @Valid @RequestBody request: UpdatePasswordRequest
    ): ResponseEntity<String> {

        authService.updatePassword(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("성공적으로 비밀번호가 변경되었습니다")
    }

}