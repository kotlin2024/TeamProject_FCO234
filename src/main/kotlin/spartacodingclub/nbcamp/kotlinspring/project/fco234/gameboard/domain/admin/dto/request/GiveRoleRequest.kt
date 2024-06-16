package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity.UserRole

data class GiveRoleRequest(
    val userId:Long,
    val giveRole: UserRole
)
