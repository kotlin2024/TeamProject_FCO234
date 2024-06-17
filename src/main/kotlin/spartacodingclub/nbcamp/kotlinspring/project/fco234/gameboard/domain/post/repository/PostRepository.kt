package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByChannelId(channelId: Long): List<Post>
    fun findByChannelIdAndId(channelId: Long, id: Long): Post?
}
