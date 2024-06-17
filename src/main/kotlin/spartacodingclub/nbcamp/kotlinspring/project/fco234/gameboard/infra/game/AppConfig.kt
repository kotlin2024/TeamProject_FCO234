package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.game

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate{
        return RestTemplate()
    }
}