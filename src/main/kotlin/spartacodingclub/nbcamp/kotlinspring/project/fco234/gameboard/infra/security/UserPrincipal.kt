package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal (

    val id:Long,
    val birthday:String,
    val email:String,
    val authorities:Collection<GrantedAuthority>
) {

    constructor(
        id: Long,
        birthday: String,
        email: String,
        roles: Set<String>
    ): this(
        id = id,
        birthday = birthday,
        email = email,
        roles.map{ SimpleGrantedAuthority("ROLE_$it") }
    )

}