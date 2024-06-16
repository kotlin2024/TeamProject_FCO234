package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime

@Entity
@Table(name = "admin_channel")
class AdminChannel (

    @Column(name= "title")
    var title: String,

    @Column(name= "content")
    var description: String?,

    @Column(name = "age_Limit")
    var ageLimit: Int? = null,

    @Column(name = "game_title")
    var gameTitle: String,

    @Column(name = "active")
    var active: Boolean = false // 활성화 상태를 저장하는 필드


) : BaseTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun toResponse() =
        ChannelResponse(
            id = this.id,
            title = this.title,
            description = this.description!!,
            ageLimit = this.ageLimit!!,
            gameTitle = this.gameTitle,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}