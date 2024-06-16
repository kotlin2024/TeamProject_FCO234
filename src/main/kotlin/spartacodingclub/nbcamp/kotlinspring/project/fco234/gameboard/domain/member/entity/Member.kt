package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.dto.request.UpdateProfileRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.ModelTime

@Entity
@Table(name = "member")
class Member (

    @field:NotBlank(message = "Email address must not be blank")
    @field:Email(message = "Email address should be format-valid")
    @Column(name = "email", unique = true)
    val email: String,

    @field:NotBlank(message = "Username must not be blank")
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}$",
        message = "Username should be [4, 10] letters long, only with lowercase letters and numbers."
    )
    @Column(name = "username", unique = true)
    val username: String,

    @field:NotBlank(message = "Password should not be blank")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,15}$",
        message = "Password should be at least 8 letters long and at most 15 characters long, including at least ONE uppercase, lowercase, and special letters."
    )
    @Column(name = "password", nullable = false)
    var password: String, // 비밀번호 변경이 가능함에 따라 val에서 var로 변경


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: MemberRole, // 해당부분을 val 에서 var로 수정 (관리자가 권한을 부여해야하기때문에 var로 수정)

    @Embedded
    var profile: MemberProfile,
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Embedded
    var time: ModelTime = ModelTime()


    fun updateProfile(
        request: UpdateProfileRequest
    ) {

        profile.nickname = request.nickname
        profile.introduction = request.introduction ?: ""

        time.update()
    }
}