package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.dto.response.UserResponse

@Entity
@Table(name="platform_user")
class User(
    @Column(name="email")
    val email:String,

    @Column(name="password")
    var password:String, // 비밀번호 변경이 가능함에 따라 val에서 var로 변경

    @Embedded
    var profile: Profile,

    @Column(name="introduce")
    var introduce: String?,

    @Enumerated(EnumType.STRING)
    @Column(name="role", length = 50)
    var role: UserRole, // 해당부분을 val 에서 var로 수정 (관리자가 권한을 부여해야하기때문에 var로 수정)



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
        introduce = introduce
    )
}