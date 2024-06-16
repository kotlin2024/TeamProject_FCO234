package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime


@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener::class)
class Post (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    val member: Member? =null,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "channel_id") // 채널의 ID만 저장할 컬럼
    var channelId: Long? = null, // 채널의 ID를 저장할 변수



) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

fun Post.toResponse(): PostResponse {

    return PostResponse(
        id = id!!,
        title= title,
        content= content,
        createdAt = createdAt,
        updateAt = updatedAt
    )
}

