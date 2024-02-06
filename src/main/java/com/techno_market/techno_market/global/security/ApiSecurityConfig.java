package com.techno_market.techno_market.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/gen/**", "/api/user/**", "/api/articles/**", "/api/answers/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .disable()
                )
                .cors(cors -> cors
                        .configure(http)
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .disable()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

