package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.request.UpdateUserProfile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.response.UserResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.service.UserProfileService

@RequestMapping("/api/users/profile")
@RestController
class UserProfileController(
    private val userProfileService: UserProfileService
) {

    @GetMapping
    fun getUserProfile(@RequestParam userId: Long):ResponseEntity<UserResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getUserProfile(userId))
    }

    @GetMapping("/my_profile") //사실 USERiD 필요없긴함
    fun getMyProfile():ResponseEntity<UserResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getMyProfile())
    }

    @PutMapping
    fun updateProfile(@Valid @RequestBody updateUserProfile: UpdateUserProfile):ResponseEntity<UserResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.updateProfile(updateUserProfile))
    }
}