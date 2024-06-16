package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.response

data class UserResponse(

    val id:Long,
    val email:String,
    val name:String,
    val birthday:String,
    val role:String,
    var introduce: String?
)