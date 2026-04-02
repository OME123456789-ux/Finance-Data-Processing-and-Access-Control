package com.example.finance.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Finance Dashboard API",
        version = "1.0.0",
        description = "Spring Boot backend with JWT authentication and role-based access control."
    )
)
public class OpenApiConfig {
}

