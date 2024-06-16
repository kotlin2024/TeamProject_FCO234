package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByPostId(postId: Long): List<Comment>

}