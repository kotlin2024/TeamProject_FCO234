package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game.service


import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class IGDBService(
    private val restTemplate: RestTemplate
) {

    @Value("\${twitch.client-id}")
    lateinit var clientId: String

    fun checkGameExists(accessToken: String, gameName: String): Boolean {
        val headers = HttpHeaders().apply {
            set("Client-ID", clientId)
            set("Authorization", "Bearer $accessToken")
            set("content-type", "text/plain")
        }
        val query = "search $gameName; fields name;"
        println("query: $query")
        val entity = HttpEntity<String>(query, headers)
        val response = try {
            val response = restTemplate.exchange(
                "https://api.igdb.com/v4/games",
                HttpMethod.POST,
                entity,
                String::class.java
            )
            println("Response: ${response.body}")
            response
        } catch (e: HttpClientErrorException) {
            println("Error: ${e.responseBodyAsString}")
            ResponseEntity(e.responseBodyAsString, e.statusCode)
        }

        return response.body?.contains(gameName) ?: false
    }

    fun getGamesByName(accessToken: String, gameName: String): ResponseEntity<String> {
        val headers = HttpHeaders().apply {
            set("Client-ID", clientId)
            set("Authorization", "Bearer $accessToken")
            set("content-type", "text/plain")
        }
        val query = "search $gameName; fields name;"
        println("query: $query")
        val entity = HttpEntity<String>(query, headers)
        println("entity: $entity")
        return try {
            val response = restTemplate.exchange(
                "https://api.igdb.com/v4/games",
                HttpMethod.POST,
                entity,
                String::class.java
            )
            println("Response: ${response.body}")
            response
        } catch (e: HttpClientErrorException) {
            println("Error: ${e.responseBodyAsString}")
            ResponseEntity(e.responseBodyAsString, e.statusCode)
        }
    }
}