package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto

import java.util.*

data class UserResponse(
    val id:Long,
    val loginId:String,
    val loginPassword:String,
    val loginNickname:String,
    val birthday:String,
    val role:String,
)
