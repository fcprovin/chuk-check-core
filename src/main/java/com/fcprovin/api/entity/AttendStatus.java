package com.fcprovin.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendStatus implements EnumType {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    REASON("사유");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
