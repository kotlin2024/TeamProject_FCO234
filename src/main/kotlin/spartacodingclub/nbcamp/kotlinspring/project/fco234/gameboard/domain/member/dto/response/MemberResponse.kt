package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member

data class MemberResponse(

    val id: Long,
    val email: String,
    val username: String,
    val role: String,

    val nickname: String,
    val birthday: String,
    var introduction: String
) {

    companion object {
        fun from(member: Member) =
            MemberResponse(
                id = member.id!!,
                email = member.email,
                username = member.username,
                nickname = member.profile.nickname,
                birthday = member.profile.birthday,
                introduction = member.profile.introduction,
                role = member.role.toString()
            )
    }
}