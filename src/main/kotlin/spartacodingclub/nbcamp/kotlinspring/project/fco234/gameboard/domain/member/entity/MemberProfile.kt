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

    @field:NotBlank(message = "생일은 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "생일은 YYYY-MM-DD 형식이어야 합니다."
    )
    @ValidBirthday
    @Column(name = "birthday", nullable = false)
    var birthday: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String
)