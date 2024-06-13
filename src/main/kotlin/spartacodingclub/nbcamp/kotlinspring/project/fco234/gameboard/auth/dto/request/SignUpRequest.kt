package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.dto.request

data class SignUpRequest(
    val email:String,
    val password:String,
    val birthday: String,// string으로 받은다음에 regex로 데이트 포맷 validation 하기?
    val name:String,
)
