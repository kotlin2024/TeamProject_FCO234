package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member

data class MemberProfileResponse(

    val id: Long,
    val username: String,
    val role: String,

    val nickname: String,
    var introduction: String
) {

    companion object {
        fun from(member: Member) =
            MemberProfileResponse(
                id = member.id!!,
                username = member.username,
                nickname = member.profile.nickname,
                introduction = member.profile.introduction,
                role = member.role.toString()
            )
    }
}