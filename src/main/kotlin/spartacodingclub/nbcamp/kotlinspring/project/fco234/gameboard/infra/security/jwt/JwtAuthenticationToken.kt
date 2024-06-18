package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

class JwtAuthenticationToken (

    private val principal: UserPrincipal,
    details: WebAuthenticationDetails
) : AbstractAuthenticationToken(principal.authorities) {

    init{
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated(): Boolean = true

}