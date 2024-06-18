package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.swagger.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(
                SecurityRequirement().addList("Bearer Authentication")
            )
            .components(
                Components().addSecuritySchemes(
                    "Bearer Authentication",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER)
                        .name("Authorization")
                )
            )
            .info(
                Info()
                    .title("Game board API")
                    .description("게임 게시판 API BY FCO234")
                    .version("1.0.0")
            )
    }

    @Bean
    fun channelAdminOpenAPI(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("Channel Admin")
            .pathsToMatch("/api/v1/channel-admin/**")
            .build()

    @Bean
    fun authOpenAPI(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("Auth")
            .pathsToMatch("/api/v1/auth/**")
            .build()

    @Bean
    fun generalOpenAPI(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("General")
            .pathsToExclude("/api/v1/auth/**", "/api/v1/channel-admin/**")
            .build()

}