package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.entity

import jakarta.persistence.*


@Entity
@Table(name="checkingpasswords")
class CheckingPassword(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=0,

    @Column(nullable = false)
    val email:String,

    @Column(nullable = false)
    val oldPassword: String,

)