package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request

data class SignUpRequest (

    val email: String,
    val username: String,
    val password: String,

    val birthday: String,
    val nickname: String
)