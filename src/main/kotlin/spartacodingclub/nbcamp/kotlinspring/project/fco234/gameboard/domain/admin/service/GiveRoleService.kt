package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository

@Service
class GiveRoleService(

    private val memberRepository: MemberRepository
) {

    fun giveRole(
        userId: Long,
        giveRole: MemberRole
    ) {

        var user = memberRepository.findByIdOrNull(userId)
            ?: throw RuntimeException("대충 예외 던져")
        user.role = giveRole
        memberRepository.save(user)
    }

}