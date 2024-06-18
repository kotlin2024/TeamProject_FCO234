package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPasswordLog

@Repository
interface MemberPasswordLogRepository : JpaRepository<MemberPasswordLog, Long>