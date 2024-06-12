package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Profile (
    @Column(name = "loginNickname")
    var loginNickname: String,

    @Column(name = "birthday")
    var birthday: String,
)