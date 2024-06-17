package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.aop.ValidBirthday

@Embeddable
data class MemberProfile (

    @Column(name = "nickname", nullable = false)
    var nickname: String,


    @Column(name = "birthday", nullable = false)
    var birthday: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String
)