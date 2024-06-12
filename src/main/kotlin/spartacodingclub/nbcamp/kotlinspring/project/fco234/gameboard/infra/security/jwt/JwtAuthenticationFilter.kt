package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
): OncePerRequestFilter(){

    companion object{
        private val BEARER_PATTERN = Regex("^Bearer (.+)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if(jwt !=null){
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val userId=it.payload.subject.toLong()
                    val role= it.payload.get("role",String::class.java)
                    val birthday =it.payload.get("birthday",String::class.java)
                    val email = it.payload.get("email",String::class.java)

                    val principal = UserPrincipal(
                        id=userId, birthday = birthday,email=email, roles = setOf(role))
                    val authentication = JwtAuthenticationToken(
                        principal=principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request) // request에서 ip주소와 세션ID를 추출해서 WebAuthenticationDetails 객체를 생성
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
        }

        filterChain.doFilter(request,response) // 현재 필터 끝내고 다음필터로 진행시켜!
    }

    private fun HttpServletRequest.getBearerToken():String?{
        val headerValue= this.getHeader(HttpHeaders.AUTHORIZATION)?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }

}