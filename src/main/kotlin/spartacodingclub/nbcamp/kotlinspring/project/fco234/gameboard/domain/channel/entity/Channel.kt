package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.ModelTime

@Entity
@Table(name = "channel")
class Channel (

    @Embedded
    var profile: ChannelProfile,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = true)
    var administrator: Member?
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Embedded
    val time: ModelTime = ModelTime()

    @Enumerated(EnumType.STRING)
    var status: ChannelStatus = ChannelStatus.OPEN


    fun updateFrom(request: UpdateChannelRequest) {
        profile.title = request.title
        profile.description = request.description ?: ""
        profile.ageLimit = request.ageLimit

        time.update()
    }
}
