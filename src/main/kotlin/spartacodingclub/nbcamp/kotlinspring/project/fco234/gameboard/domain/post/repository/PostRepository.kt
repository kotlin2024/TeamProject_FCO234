package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.Post

interface PostRepository : JpaRepository<Post, Long> {

}
