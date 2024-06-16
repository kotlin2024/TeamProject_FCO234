package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity.User

interface UserRepository: JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}