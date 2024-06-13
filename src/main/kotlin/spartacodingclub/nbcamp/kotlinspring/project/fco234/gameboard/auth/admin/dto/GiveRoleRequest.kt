package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.admin.dto

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.UserRole

data class GiveRoleRequest(
    val userId:Long,
    val giveRole: UserRole
)
