package com.fcprovin.api.config;

import com.fcprovin.api.config.handler.AccessDeniedExceptionHandler;
import com.fcprovin.api.config.handler.AuthenticationExceptionHandler;
import com.fcprovin.api.config.jwt.JwtAuthenticationFilter;
import com.fcprovin.api.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider provider;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(r -> r
                        .requestMatchers(GET,
                                "/",
                                "/favicon.ico",
                                "/docs/index.html",
                                "/exception/authentication",
                                "/exception/access-denied",
                                "/api/v1/sns").permitAll()
                        .requestMatchers(POST,
                                "/api/v1/auth/access-token",
                                "/api/v1/sns",
                                "/api/v1/member").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationExceptionHandler)
                        .accessDeniedHandler(accessDeniedExceptionHandler))
                .addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
