package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.admin.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.UserRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.repository.UserRepository

@Service
class GiveRoleService(
    private val userRepository: UserRepository,
) {
    fun giveRole(userId:Long,giveRole:UserRole) {
        var user=userRepository.findByIdOrNull(userId) ?: throw RuntimeException("대충 예외 던져")
        user.role= giveRole
        userRepository.save(user)
    }

}