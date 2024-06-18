package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channelmemberposition.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition

@Entity
@Table(name = "channel_member_position")
class ChannelMemberPosition (

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    val channel: Channel,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    var position: MemberPosition = MemberPosition.MEMBER
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
