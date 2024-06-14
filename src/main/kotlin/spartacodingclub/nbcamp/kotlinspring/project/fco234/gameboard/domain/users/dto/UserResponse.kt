package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.dto

import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.UserRole

data class UserResponse(
    val id:Long,
    val email:String,
    val name:String,
    val birthday:String,
    val role:String,
    var introduce: String?
)
