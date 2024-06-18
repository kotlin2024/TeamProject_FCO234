package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdatePasswordRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdateProfileRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.service.MemberService

@RequestMapping("/api/v1/users/profile")
@RestController
class MemberController (

    private val memberService: MemberService
) {

    @GetMapping
    fun getUserProfile(
        @RequestParam userId: Long
    ): ResponseEntity<MemberResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getUserProfile(userId))


    @GetMapping("/my_profile")
    fun getMyProfile(): ResponseEntity<MemberResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getMyProfile())


    @PutMapping
    fun updateProfile(
        @RequestBody updateProfileRequest: UpdateProfileRequest
    ): ResponseEntity<MemberResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.updateProfile(updateProfileRequest))


    @PatchMapping("/update-password")
    fun updatePassword(
        @RequestBody request: UpdatePasswordRequest
    ): ResponseEntity<String> {

        memberService.updatePassword(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("성공적으로 비밀번호가 변경되었습니다")
    }

}