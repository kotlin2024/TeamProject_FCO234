package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.entity.ChannelMemberPosition

@Repository
interface ChannelMemberPositionRepository : JpaRepository<ChannelMemberPosition, Long> {

    fun findAllByChannelId(channelId: Long): List<ChannelMemberPosition>
    fun findByChannelIdAndMemberId(channelId: Long, memberId: Long): ChannelMemberPosition?

    fun existsByChannelIdAndMemberId(channelId: Long, memberId: Long): Boolean
}