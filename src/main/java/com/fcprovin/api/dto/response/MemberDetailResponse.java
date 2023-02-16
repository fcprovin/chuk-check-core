package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Member;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@SuperBuilder
public class MemberDetailResponse extends MemberResponse {

    private final SnsResponse sns;
    private final List<PlayerResponse> players;

    public static MemberDetailResponse of(Member member) {
        return MemberDetailResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birthDate(member.getBirthDate())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .sns(SnsResponse.of(member.getSns()))
                .players(member.getPlayers().stream().map(PlayerResponse::of).collect(toList()))
                .build();
    }
}
