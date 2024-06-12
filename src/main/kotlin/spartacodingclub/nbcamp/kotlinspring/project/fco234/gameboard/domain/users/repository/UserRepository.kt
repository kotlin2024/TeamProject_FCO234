package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.User

interface UserRepository: JpaRepository<User, Long> {

    //fun findByEmail(email:String): User?

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}