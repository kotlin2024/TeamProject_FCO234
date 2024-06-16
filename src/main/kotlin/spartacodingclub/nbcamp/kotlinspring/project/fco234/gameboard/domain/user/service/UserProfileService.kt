package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.service


import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.request.UpdateUserProfile
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.response.UserResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.repository.UserRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Service
class UserProfileService(
    private val userRepository: UserRepository,
) {

    fun getUserProfile(userId:Long): UserResponse {
        val user=userRepository.findByIdOrNull(userId)?:throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")
        return user.toResponse()
    }

    fun getMyProfile(): UserResponse {
        val authentication=SecurityContextHolder.getContext().authentication
        val principal= authentication.principal as UserPrincipal

        val user=userRepository.findByIdOrNull(principal.id)?:throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")
        return user.toResponse()
    }

    fun updateProfile(request: UpdateUserProfile): UserResponse {
        val authentication=SecurityContextHolder.getContext().authentication
        val principal= authentication.principal as UserPrincipal
        val user=userRepository.findByIdOrNull(principal.id)?:throw RuntimeException("User not found 요것도 예외처리 따로 해줘야함")

        if(user.profile.name==request.name) throw RuntimeException("변경하려는 이름과 기존 이름이 같음! 요것도 예외처리 따로 해줘야함")
        user.profile.name=request.name
        user.introduce=request.introduce
        userRepository.save(user)
        return user.toResponse()
    }
}