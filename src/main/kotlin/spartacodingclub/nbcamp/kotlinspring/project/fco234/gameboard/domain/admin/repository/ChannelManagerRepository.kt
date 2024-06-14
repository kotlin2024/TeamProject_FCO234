package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model.ChannelManager

interface ChannelManagerRepository : JpaRepository<ChannelManager, Long> {

}