package com.academy.taskmanager.task_manager.config;

import com.academy.taskmanager.task_manager.userservice.repository.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDaoImpl userRepository;

    public SecurityConfig(UserDaoImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .logout(logout -> logout
                .logoutUrl("/logout") // URL выхода
                .logoutSuccessHandler((request, response, authentication) -> {
                    String logoutUrl = "http://localhost:8081/realms/task-manager/protocol/openid-connect/logout?redirect_uri=http://localhost:8080/";
                    response.sendRedirect(logoutUrl);
                }))
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/login"
                        ).permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers("/teacher").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new KeycloakJwtAuthenticationConverter(userRepository)
                                )
                        )
                );

        return http.build();
    }
}

