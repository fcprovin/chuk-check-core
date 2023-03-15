package com.fcprovin.api.repository;

import com.fcprovin.api.dto.jwt.JwtRole;
import com.fcprovin.api.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findBySubjectAndRoleAndRefreshToken(Long subject, JwtRole role,String refreshToken);
}
