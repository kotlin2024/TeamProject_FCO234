package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.LoginRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.SignUpRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.UserResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.service.imsiSignupService


@RestController
class imsiSignUpController(
    private val imsiSignupService: imsiSignupService
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(imsiSignupService.login(loginRequest))
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(imsiSignupService.signUp(signUpRequest))
    }
}