package com.academy.taskmanager.task_manager.config;

import io.swagger.v3.oas.models.security.*;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Task Manager API").version("v1"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8081/realms/task-manager/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8081/realms/task-manager/protocol/openid-connect/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "Access ID token")
                                                        .addString("profile", "User profile")
                                                        .addString("email", "User email"))))))
                .addSecurityItem(new SecurityRequirement().addList("oauth2", List.of("openid")));
    }

}

