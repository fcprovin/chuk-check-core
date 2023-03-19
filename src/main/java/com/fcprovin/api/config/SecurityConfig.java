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

import static com.fcprovin.api.dto.jwt.JwtRole.ROLE_ADMIN;
import static com.fcprovin.api.dto.jwt.JwtRole.ROLE_USER;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider provider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .authorizeRequests()
                    .antMatchers(POST, "/api/**/auth/access-token", "/api/**/sns", "/api/**/member").permitAll()
                    .anyRequest().hasAnyRole(ROLE_USER.getName(), ROLE_ADMIN.getName())
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new AuthenticationExceptionHandler())
                    .accessDeniedHandler(new AccessDeniedExceptionHandler())
                    .and()
                .addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
