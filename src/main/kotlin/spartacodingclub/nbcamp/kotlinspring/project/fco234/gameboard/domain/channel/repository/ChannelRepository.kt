package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.AdminChannel

interface ChannelRepository : JpaRepository<AdminChannel, Long>