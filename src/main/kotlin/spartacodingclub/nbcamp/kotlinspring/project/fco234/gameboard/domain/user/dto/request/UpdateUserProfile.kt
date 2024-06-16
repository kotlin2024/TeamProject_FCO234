package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UpdateUserProfile(
    
    @field:NotBlank(message = "이름은 필수 입력 항목입니다.")
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}$",
        message = "이름은 4자에서 10자 사이여야 하며, 알파벳 소문자와 숫자만 포함할 수 있습니다."
    )
    val name: String,

    val introduce: String?
)