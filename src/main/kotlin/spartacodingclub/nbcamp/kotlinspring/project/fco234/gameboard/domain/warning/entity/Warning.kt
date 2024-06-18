package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.warning.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import java.time.ZonedDateTime

@Entity
@Table(name = "warning")
class Warning (

    @Column(name = "reason")
    val reason: String,

    @Embedded
    val source: Source,

    @ManyToOne
    @JoinColumn(name = "issuer_id")
    val issuer: Member,

    @ManyToOne
    @JoinColumn(name = "target_member_id")
    val targetMember: Member
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "created_at", updatable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now()
}