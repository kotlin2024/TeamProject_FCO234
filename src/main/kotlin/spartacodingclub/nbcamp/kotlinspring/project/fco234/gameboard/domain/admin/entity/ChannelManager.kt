package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member

@Entity
@Table(name = "channel_manager")
class ChannelManager(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_channel_id")
    val adminChannel: AdminChannel
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}