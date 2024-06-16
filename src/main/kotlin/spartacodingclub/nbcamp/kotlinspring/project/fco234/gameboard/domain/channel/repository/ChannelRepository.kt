package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.AdminChannel

@Repository
interface ChannelRepository : JpaRepository<AdminChannel, Long>