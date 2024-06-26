package umc.dofarming.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        // SecuritySecheme명
        String jwtSchemeName = "JWT";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer"));


        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(apiInfo());

    }



    private Info apiInfo() {
        return new Info()
                .title("DonFarming API 명세서")
                .description("Swagger UI for DonFarming 서비스")
                .version("v3");
    }
}
