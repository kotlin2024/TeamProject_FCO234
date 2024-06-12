package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id:Long,
    val birthday:String,
    val authorities:Collection<GrantedAuthority>
){
    constructor(id: Long, birthday: String, roles: Set<String>):this(
        id,
        birthday,
        roles.map{ SimpleGrantedAuthority("ROLE_$it") }
    )
}