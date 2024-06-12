package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.dto.UserResponse

@Entity
@Table(name="platform_users")
class Users(
    @Column(name="loginId")
    val loginId:String,

    @Column(name="loginPassword")
    val loginPassword:String,


    @Embedded
    var profile:Profile,

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    val role:UserRole
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
}
fun Users.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        loginId = loginId,
        loginPassword = loginPassword,
        birthday =  profile.birthday,
        loginNickname = profile.loginNickname,
        role = role.name,
    )
}