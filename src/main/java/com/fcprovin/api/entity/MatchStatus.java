package com.fcprovin.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatus implements EnumType {

    CREATE("생성"),
    VOTE("투표"),
    ATTEND("출석"),
    MATCH("진행"),
    FINISH("완료");

    private final String description;

    @Override
    public String getName() {
        return name();
    }
}
