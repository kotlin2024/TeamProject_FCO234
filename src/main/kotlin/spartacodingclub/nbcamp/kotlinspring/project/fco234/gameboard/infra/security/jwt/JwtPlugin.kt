package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberPosition
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin (

    @Value("\${auth.jwt.issuer}")
    private val issuer: String,

    @Value("\${auth.jwt.secret}")
    private val secret: String
) {

    fun validateToken(
        jwt: String
    ): Result<Jws<Claims>> =

        kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
        }


     fun generateToken(
         subject: String,
         role: MemberPosition,
         birthday: String,
         expirationPeriod: Duration,
         email: String
     ): String{

        val claims: Claims = Jwts.claims()
            .add(mapOf(
                "role" to role,
                "birthday" to birthday,
                "email" to email))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

}