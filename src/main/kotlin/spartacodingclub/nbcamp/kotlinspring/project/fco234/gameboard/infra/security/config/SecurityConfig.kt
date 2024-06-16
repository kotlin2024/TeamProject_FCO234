package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.CustomAuthenticationEntryPoint
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.jwt.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity // 통신할때 관련 보안 기능을 키기 위해서 설정
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,//
    private val accessDeniedHandler: AccessDeniedHandler,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic{ it.disable() } //disable로 필요없는 필터를 제외시킴
            .formLogin { it.disable() }
            .csrf{ it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/login",
                    "/signup",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api/admin/channels/**",
                    "/api/v1/posts/get/**",
                    "/api/v1/posts/{postId}/comments/get/**",
                    "/admin/**",
                    "/verify-email",
                    "/api/users/profile/**", //해당부분은 테스트용으로 작성한 것이며 추후에 해당ㅂ분을 삭제하고 프로필 컨트롤러에  preAuthorize를 넣어야함
                    "/api/users/update-password"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling{
                it.authenticationEntryPoint(authenticationEntryPoint) //예외처리들
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .build()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {

        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_CHANNEL_ADMIN \n ROLE_CHANNEL_ADMIN > ROLE_CHANNEL_MANAGER \n ROLE_CHANNEL_MANAGER > ROLE_CHANNEL_USER \n ROLE_CHANNEL_USER > ROLE_PLATFORM_USER")

    }

}