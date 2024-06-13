package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.model


import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.User

@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener::class)
class Comment (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable=false)
    val user :User,


    @Column(name="content")
    var content:String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cardId", nullable = false)
    val post: Post

): BaseTime()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? =null
}
fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        postId = post.id!!,
        content= content,
        createdAt = createdAt,
        updatedAt = updatedAt,

    )
}