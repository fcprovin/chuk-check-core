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
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .authorizeRequests()
                    .antMatchers(GET, "/", "/docs/**", "/exception/**").permitAll()
                    .antMatchers(POST, "/api/**/auth/access-token", "/api/**/sns", "/api/**/member").permitAll()
                    .anyRequest().hasAnyRole("USER", "ADMIN")
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationExceptionHandler)
                    .accessDeniedHandler(accessDeniedExceptionHandler)
                    .and()
                .addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
