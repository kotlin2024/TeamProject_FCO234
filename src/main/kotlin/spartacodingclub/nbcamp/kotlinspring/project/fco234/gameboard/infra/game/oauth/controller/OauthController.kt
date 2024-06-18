package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.oauth.controller

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service.AccessTokenHolder

@Controller
class OauthController(
    private val restTemplate: RestTemplate,
    private val accessTokenHolder: AccessTokenHolder
) {
    @Value("\${twitch.client-id}")
    lateinit var clientId: String

    @Value("\${twitch.client-secret}")
    lateinit var clientSecret: String

    @Value("\${twitch.redirect-uri}")
    lateinit var redirectUri: String

    private val authUrl = "https://id.twitch.tv/oauth2/authorize"
    private val tokenUrl = "https://id.twitch.tv/oauth2/token"




    @PostMapping("/get-token")
    fun getAccessToken(): ResponseEntity<String>{
        val requestBody = mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "grant_type" to "client_credentials"
        )

        val response = restTemplate.postForEntity(tokenUrl, requestBody, JsonNode::class.java)
        val token = response.body?.get("access_token")?.asText()
        val expiresIn = response.body?.get("expires_in")?.asLong()
        println(expiresIn)
        token?.let{ accessTokenHolder.accessToken = it}
        return ResponseEntity.ok(token)
    }


}