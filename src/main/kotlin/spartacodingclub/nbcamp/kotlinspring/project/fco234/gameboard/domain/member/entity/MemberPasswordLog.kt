package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "member_password_log")
class MemberPasswordLog (

    @OneToOne
    @JoinColumn(name = "member_id")
    val member: Member
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "password_a", length = 16, nullable = false)
    private var passwordA: String = ""

    @Column(name = "password_b", length = 16, nullable = false)
    private var passwordB: String = ""

    @Column(name = "password_c", length = 16, nullable = false)
    private var passwordC: String = ""

    val passwords: List<String>
        get() = listOf(passwordA, passwordB, passwordC)


    fun add(
        newPassword: String
    ) {

        passwordA = passwordB
        passwordB = passwordC
        passwordC = newPassword
    }

}