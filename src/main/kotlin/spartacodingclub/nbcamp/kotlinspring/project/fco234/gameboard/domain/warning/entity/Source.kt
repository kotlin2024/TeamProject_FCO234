package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.warning.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel

@Embeddable
data class Source (

    @Column(name = "source_type")
    @Enumerated(EnumType.STRING)
    val sourceType: SourceType,

    @Column(name = "source_id")
    val sourceId: Long,

    @ManyToOne
    @JoinColumn(name = "source_channel_id")
    val sourceChannel: Channel
)