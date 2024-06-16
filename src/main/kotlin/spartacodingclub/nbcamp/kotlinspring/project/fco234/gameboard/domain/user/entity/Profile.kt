package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Profile (
    @Column(name = "name")
    var name: String,

    @Column(name = "birthday")
    var birthday: String,
)