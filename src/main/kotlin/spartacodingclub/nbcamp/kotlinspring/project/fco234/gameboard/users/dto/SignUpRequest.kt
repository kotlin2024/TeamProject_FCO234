package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto

data class SignUpRequest(
    val loginId:String,
    val loginPassword:String,
    val birthday: String,// string으로 받은다음에 regex로 데이트 포맷 validation 하기?
    val loginNickname:String,
)
