package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal

class JwtAuthenticationToken( //사용자 인증정보 캡슐화 하는 클래스 -> 보안,일관성,유연성,테스트 용이성에 이점이 있음
    private val principal: UserPrincipal,
    details: WebAuthenticationDetails
): AbstractAuthenticationToken(principal.authorities) {

    init{
        super.setAuthenticated(true) // 이토큰이 이미 인증된 상태
        super.setDetails(details) // 요청의 추가정보 설정
    }

    override fun getCredentials() = null // JWT 에서는 자격증명이!!!! 필요가 없으니  null 반환

    override fun getPrincipal() = principal // 현재 인증된 사용자인 principal 객체 반환

    override fun isAuthenticated():Boolean{ //jwtPlugin.validateToken(jwt)에서 검증했으니 당연히 return true
        return true
    }
}