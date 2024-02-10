package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.dto.jwt.JwtCreate;
import com.fcprovin.api.dto.jwt.JwtRole;
import com.fcprovin.api.entity.Token;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class TokenCreateRequest {

    @NotEmpty
    private Long subject;

    @NotEmpty
    private JwtRole scope;

    @Builder
    public TokenCreateRequest(Long subject, JwtRole scope) {
        this.subject = subject;
        this.scope = scope;
    }

    public Token toEntity(JwtCreate create) {
        return Token.builder()
                .subject(subject)
                .role(scope)
                .refreshToken(create.getToken())
                .expireDate(create.getExpire())
                .build();
    }
}
