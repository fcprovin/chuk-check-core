package com.fcprovin.api.service;

import com.fcprovin.api.config.jwt.JwtProvider;
import com.fcprovin.api.dto.jwt.JwtCreate;
import com.fcprovin.api.dto.jwt.JwtToken;
import com.fcprovin.api.dto.request.create.TokenCreateRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.fcprovin.api.dto.jwt.JwtRole.ROLE_USER;
import static com.fcprovin.api.dto.jwt.JwtType.REFRESH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class TokenServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    TokenService tokenService;

    @Autowired
    JwtProvider provider;

    @Test
    void validateRefreshToken() {
        //given
        JwtToken token = JwtToken.builder()
                .subject(-1L)
                .scope(ROLE_USER)
                .build();

        JwtCreate jwt = provider.createJwt(JwtToken.builder()
                .subject(-1L)
                .scope(ROLE_USER)
                .type(REFRESH)
                .build());

        //then
        assertThatThrownBy(() -> tokenService.validateRefreshToken(token, jwt.getToken()), "Not found refresh token")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateRequestSubject() {
        //given
        TokenCreateRequest request = TokenCreateRequest.builder()
                .scope(ROLE_USER)
                .build();

        JwtCreate jwt = provider.createJwt(JwtToken.builder()
                .subject(-1L)
                .scope(ROLE_USER)
                .type(REFRESH)
                .build());

        assertThatThrownBy(() -> tokenService.create(request, jwt), "Required subject")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateRequestScope() {
        //given
        TokenCreateRequest request = TokenCreateRequest.builder()
                .subject(-1L)
                .build();

        JwtCreate jwt = provider.createJwt(JwtToken.builder()
                .subject(-1L)
                .scope(ROLE_USER)
                .type(REFRESH)
                .build());

        assertThatThrownBy(() -> tokenService.create(request, jwt), "Required scope")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateJwtToken() {
        //given
        TokenCreateRequest request = TokenCreateRequest.builder()
                .subject(1L)
                .scope(ROLE_USER)
                .build();

        JwtCreate jwt = JwtCreate.builder()
                .expire(LocalDateTime.now())
                .build();

        assertThatThrownBy(() -> tokenService.create(request, jwt), "Required token")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateJwtExpire() {
        //given
        TokenCreateRequest request = TokenCreateRequest.builder()
                .subject(1L)
                .scope(ROLE_USER)
                .build();

        JwtCreate jwt = JwtCreate.builder()
                .token("refreshToken")
                .expire(LocalDateTime.MIN)
                .build();

        assertThatThrownBy(() -> tokenService.create(request, jwt), "Invalid expire date")
                .isInstanceOf(IllegalArgumentException.class);
    }
}