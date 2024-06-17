package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.response.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.ModelTime

@Entity
@Table(name = "comment")
class Comment (

    @Column(name = "content")
    var content: String,


    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val author: Member,

    @ManyToOne
    @JoinColumn(name ="post_id", nullable = false)
    val post: Post

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Embedded
    val time: ModelTime = ModelTime()


    fun update(request: UpdateCommentRequest) {
        content = request.content
        time.update()
    }

}