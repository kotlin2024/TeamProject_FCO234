package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.User

@Entity
@Table(name = "channel_manager")
class ChannelManager(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_channel_id")
    val adminChannel: AdminChannel
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}