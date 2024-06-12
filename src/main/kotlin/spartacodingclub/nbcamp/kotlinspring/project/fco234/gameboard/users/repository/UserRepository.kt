package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.users.model.Users

interface UserRepository: JpaRepository<Users, Long> {

    //fun findByEmail(email:String): User?

    fun existsByLoginId(loginId: String): Boolean

    fun findByLoginId(loginId: String): Users?
}