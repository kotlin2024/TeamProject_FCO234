package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.ZonedDateTime


@Embeddable
class ModelTime (

    @Column(nullable = false, updatable = false)
    val createdAt: ZonedDateTime? = ZonedDateTime.now(),

    @Column(nullable = true)
    var updatedAt: ZonedDateTime? = ZonedDateTime.now()
) {

    fun update() {
        updatedAt = ZonedDateTime.now()
    }

}