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

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "username", unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: MemberPosition,

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