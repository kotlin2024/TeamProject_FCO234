package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.controller



import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.oauth.controller.OauthController
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service.AccessTokenHolder
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service.IGDBService


@RestController
@RequestMapping("/api")
class IGDBController(
    private val igdbService: IGDBService,
    private val oauthController: OauthController,
    private val accessTokenHolder: AccessTokenHolder
) {
    @Scheduled(fixedRate = 300000) // ms
    fun updateAccessToken() {
        oauthController.getAccessToken()
    }


    @PostMapping("/games")
    fun getGamesByName(@RequestBody gameName: String): ResponseEntity<String>{
        val accessToken = accessTokenHolder.accessToken ?: throw IllegalStateException("Access token is not available")
        return igdbService.getGamesByName(accessToken, gameName)
    }
}