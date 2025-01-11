package com.project.blog_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(securityComponents())
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Dev Server"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Blogging Application : Backend Course")
                .description("This project is developed for Blog Application")
                .version("1.0")
                .contact(new Contact()
                        .name("Rashid Ansari")
                        .url("https://rashid-portfolio-786-qgj6scz83-rashiddeveloper786s-projects.vercel.app/")
                        .email("rashidalpha786@gmail.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes("JWT", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(AUTHORIZATION_HEADER));
    }
}
