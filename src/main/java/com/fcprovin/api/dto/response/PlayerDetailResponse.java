package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Player;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class PlayerDetailResponse extends PlayerResponse {

    private final MemberResponse member;
    private final TeamResponse team;

    public static PlayerDetailResponse of(Player player) {
        return PlayerDetailResponse.builder()
                .id(player.getId())
                .uniformNumber(player.getUniformNumber())
                .position(player.getPosition())
                .status(player.getStatus())
                .authority(player.getAuthority())
                .createdDate(player.getCreatedDate())
                .updatedDate(player.getUpdatedDate())
                .member(MemberResponse.of(player.getMember()))
                .team(TeamResponse.of(player.getTeam()))
                .build();
    }
}
