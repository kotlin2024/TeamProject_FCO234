package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.ChannelStatus

@Repository
interface ChannelRepository : JpaRepository<Channel, Long> {

    fun findAllByStatus(status: ChannelStatus): List<Channel>
    fun existsByProfileGameTitle(gameTitle: String): Boolean
}