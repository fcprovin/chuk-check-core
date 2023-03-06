package com.fcprovin.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlayerAuthority implements EnumType {

    GENERAL("일반"),
    MANAGER("임원"),
    LEADER("리더");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
