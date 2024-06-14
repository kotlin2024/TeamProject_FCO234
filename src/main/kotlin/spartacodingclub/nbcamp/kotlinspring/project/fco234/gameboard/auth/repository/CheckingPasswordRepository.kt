package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.model.CheckingPassword

interface CheckingPasswordRepository: JpaRepository<CheckingPassword, Long> {
    fun findTop3ByEmailOrderByIdDesc(userEmail: String): List<CheckingPassword>

    @Transactional
    fun deleteByEmailAndIdLessThanEqualOrderByOldPasswordAsc(email: String, id: Long)
}