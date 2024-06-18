package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request

import jakarta.persistence.Column
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.aop.ValidBirthday

data class SignUpRequest (

    @field:NotBlank(message = "Email address must not be blank")
    @field:Email(message = "Email address should be format-valid")

    val email: String,

    @field:NotBlank(message = "Username must not be blank")
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}$",
        message = "Username should be [4, 10] letters long, only with lowercase letters and numbers."
    )
    val username: String,

    @field:NotBlank(message = "Password should not be blank")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,15}$",
        message = "Password should be at least 8 letters long and at most 15 characters long, including at least ONE uppercase, lowercase, and special letters."
    )
    val password: String,

    @field:NotBlank(message = "생일은 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "생일은 YYYY-MM-DD 형식이어야 합니다."
    )
    @ValidBirthday
    val birthday: String,
    val nickname: String
)