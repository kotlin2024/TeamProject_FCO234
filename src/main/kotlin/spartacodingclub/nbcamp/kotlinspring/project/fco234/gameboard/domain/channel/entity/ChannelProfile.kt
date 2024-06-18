package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity

import jakarta.persistence.Column

data class ChannelProfile(

    @Column(name = "title", nullable = false)
    var title: String = "",

    @Column(name = "description", nullable = false)
    var description: String = "",

    @Column(name = "age_limit", nullable = false)
    var ageLimit: Int = 0,

    @Column(name = "game_title", nullable = false, unique = true)
    var gameTitle: String = ""
)