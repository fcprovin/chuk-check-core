package com.fcprovin.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseStatus implements EnumType {

    WAIT("대기"),
    APPROVE("승인"),
    EXIT("탈퇴");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
