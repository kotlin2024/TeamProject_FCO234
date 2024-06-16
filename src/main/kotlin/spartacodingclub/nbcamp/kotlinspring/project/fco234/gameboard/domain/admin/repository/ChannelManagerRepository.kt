package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity.ChannelManager

@Repository
interface ChannelManagerRepository : JpaRepository<ChannelManager, Long> {

}