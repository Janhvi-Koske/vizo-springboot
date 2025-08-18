package com.vizo.vizo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**").authenticated() // secure only /api/**
                .anyRequest().permitAll() // everything else allowed
            )
            .httpBasic().and()
            .csrf(csrf -> csrf.disable()); // disable CSRF for APIs

        return http.build();
    }
}
