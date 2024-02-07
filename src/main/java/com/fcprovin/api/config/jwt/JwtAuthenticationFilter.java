package com.fcprovin.api.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider provider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) request);

        if (nonNull(token)) {
            provider.validateToken(token);
            getContext().setAuthentication(provider.getAuthentication(token));
        }

        chain.doFilter(request, response);
    }
}
