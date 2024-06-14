package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.channel.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.users.model.User


@Entity
@Table(name = "gameboard")
class Channel(
    var name: String,
    var description: String,
){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0


//    @OneToMany(mappedBy = "channel", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    val post: List<Post> = emptyList()

}
