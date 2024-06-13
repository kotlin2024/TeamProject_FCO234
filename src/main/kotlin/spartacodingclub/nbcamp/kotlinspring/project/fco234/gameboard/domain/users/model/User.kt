package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.dto.UserResponse

@Entity
@Table(name="platform_user")
class User(
    @Column(name="email")
    val email:String,

    @Column(name="password")
    val password:String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    val role: UserRole,



){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
}
fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        email = email,
        birthday =  profile.birthday,
        name = profile.name,
        role = role.name,
    )
}