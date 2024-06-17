package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service

import org.springframework.stereotype.Component

@Component
class AccessTokenHolder {
    @Volatile
    var accessToken : String? = null

        @Synchronized set

}