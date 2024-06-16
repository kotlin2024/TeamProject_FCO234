package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.entity.User

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?

}