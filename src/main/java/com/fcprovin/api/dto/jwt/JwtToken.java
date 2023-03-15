package com.fcprovin.api.dto.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {

    private final Long subject;
    private final JwtRole scope;
    private final JwtType type;

    @Builder
    public JwtToken(Long subject, JwtRole scope, JwtType type) {
        this.subject = subject;
        this.scope = scope;
        this.type = type;
    }
}
