package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
class GameBoardApplication

fun main(args: Array<String>) {
    runApplication<GameBoardApplication>(*args)
}
