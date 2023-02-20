package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PlayerResponse {

    private final Long id;
    private final int uniformNumber;
    private final Position position;
    private final BaseStatus status;
    private final PlayerAuthority authority;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final MemberResponse member;
    private final TeamResponse team;

    public static PlayerResponse of(Player player) {
        return PlayerResponse.builder()
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
