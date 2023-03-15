package com.fcprovin.api.dto.jwt;

import com.fcprovin.api.entity.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtRole implements EnumType {
    ROLE_USER("사용자"), ROLE_ADMIN("관리자");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
