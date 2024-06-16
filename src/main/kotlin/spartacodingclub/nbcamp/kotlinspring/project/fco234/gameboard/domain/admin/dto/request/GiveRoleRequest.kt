package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole

data class GiveRoleRequest (

    val userId: Long,
    val giveRole: MemberRole
)