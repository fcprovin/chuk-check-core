package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.fcprovin.api.dto.jwt.JwtRole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class TokenRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    TokenRepository tokenRepository;

    @Test
    void save() {
        //given
        Token token = new Token(ROLE_USER, 1L, "refreshToken", LocalDateTime.now());

        //when
        Token saveToken = tokenRepository.save(token);
        Token findToken = tokenRepository.findById(saveToken.getId()).get();

        //then
        assertThat(findToken).isEqualTo(token);
        assertThat(findToken.getId()).isEqualTo(token.getId());
        assertThat(findToken.getRole()).isEqualTo(token.getRole());
        assertThat(findToken.getSubject()).isEqualTo(token.getSubject());
        assertThat(findToken.getRefreshToken()).isEqualTo(token.getRefreshToken());
    }

    @Test
    void findByRoleAndSubject() {
        //given
        Token token = new Token(ROLE_USER, 1L, "refreshToken", LocalDateTime.now());
        em.persist(token);

        //when
        Token findToken = tokenRepository.findBySubjectAndRoleAndRefreshToken(1L, ROLE_USER, token.getRefreshToken()).get();

        //then
        assertThat(findToken).isEqualTo(token);
        assertThat(findToken.getId()).isEqualTo(token.getId());
        assertThat(findToken.getRole()).isEqualTo(token.getRole());
        assertThat(findToken.getSubject()).isEqualTo(token.getSubject());
        assertThat(findToken.getRefreshToken()).isEqualTo(token.getRefreshToken());
    }
}