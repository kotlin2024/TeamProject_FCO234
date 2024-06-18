package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member

@Repository
interface MemberRepository: JpaRepository<Member, Long> {

    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Member?
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Member?
}