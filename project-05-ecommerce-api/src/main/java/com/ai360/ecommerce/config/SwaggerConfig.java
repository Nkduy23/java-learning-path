package com.ai360.ecommerce.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SWAGGER CONFIG
 * ===============
 * Cau hinh Swagger UI tai: http://localhost:8080/swagger-ui.html
 * Tu dong doc @RestController sinh ra trang test API truc quan.
 *
 * SecurityScheme: them o "Authorize" de test API can dang nhap.
 * Dan token vao: Bearer <token>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .description("REST API cho he thong thuong mai dien tu — Project 05/05")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AI360.ASIA")
                                .url("https://ai360.asia")))
                // Them nut "Authorize" tren Swagger UI de nhap JWT token
                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Nhap JWT token sau khi dang nhap")));
    }
}
