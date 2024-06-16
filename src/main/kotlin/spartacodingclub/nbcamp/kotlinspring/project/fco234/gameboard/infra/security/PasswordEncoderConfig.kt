package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfig { // 비밀번호 인코딩!

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

}