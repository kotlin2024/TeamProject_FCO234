package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.entity.Channel
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.ModelTime


@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener::class)
class Post (

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    val author: Member,

    @ManyToOne
    @JoinColumn(name = "channel_id") // 채널의 ID만 저장할 컬럼
    val channel: Channel // 채널의 ID를 저장할 변수
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Embedded
    val time: ModelTime = ModelTime()


    fun update(request: UpdatePostRequest) {
        title = request.title
        content = request.content

        time.update()
    }

}