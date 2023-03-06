package com.fcprovin.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnsType implements EnumType {

    GOOGLE("구글"),
    APPLE("애플"),
    NAVER("네이버"),
    KAKAO("카카오");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
