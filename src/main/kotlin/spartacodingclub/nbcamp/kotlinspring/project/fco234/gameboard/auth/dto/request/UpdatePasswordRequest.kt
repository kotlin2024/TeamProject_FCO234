package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UpdatePasswordRequest(

    @field:NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,15}$",
        message = "비밀번호는 8자에서 15자 사이여야 하며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    val updatePassword:String,

)
