package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.aop.ValidBirthday

data class SignUpRequest(
    @field:NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @field:Email(message = "유효한 이메일 주소를 입력해주세요.")
    val email:String,


    @field:NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,15}$",
        message = "비밀번호는 8자에서 15자 사이여야 하며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    val password:String,


    @field:NotBlank(message = "생일은 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "생일은 YYYY-MM-DD 형식이어야 합니다."
    )
    @ValidBirthday
    val birthday: String,


    @field:NotBlank(message = "이름은 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}$",
        message = "이름은 4자에서 10자 사이여야 하며, 알파벳 소문자와 숫자만 포함할 수 있습니다."
    )
    val name:String,
)
