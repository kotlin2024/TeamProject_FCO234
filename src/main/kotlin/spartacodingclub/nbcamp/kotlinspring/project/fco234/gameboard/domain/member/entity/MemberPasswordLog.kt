package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "member_password_log")
class MemberPasswordLog (

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    val member: Member
) {

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