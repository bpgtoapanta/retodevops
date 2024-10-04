package com.retotcs.demodevops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // Configurar las autorizaciones de las solicitudes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/devops", "/devops/generate-token").permitAll() // Permitir acceso al endpoint /devops
            );
        return http.build();
    }
}