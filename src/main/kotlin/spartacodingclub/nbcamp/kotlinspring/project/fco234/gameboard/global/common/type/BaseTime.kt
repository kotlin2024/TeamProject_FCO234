package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTime {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = true)
    var updatedAt: LocalDateTime? = null
}