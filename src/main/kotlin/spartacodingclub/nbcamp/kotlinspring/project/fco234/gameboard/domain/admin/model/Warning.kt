package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime

@Entity
@Table(name = "warning")
class Warning(

    @Column(name = "reason")
    val reason: String,

    @Column(name = "source")
    val source: String,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: Type,

    @Column(name = "target_id")
    val targetId: Long


):BaseTime() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}