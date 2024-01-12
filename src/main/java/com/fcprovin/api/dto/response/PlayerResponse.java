package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class PlayerResponse {

    private final Long playerId;
    private final Integer uniformNumber;
    private final Position position;
    private final BaseStatus status;
    private final PlayerAuthority authority;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final MemberResponse member;
    private final TeamResponse team;

    public static PlayerResponse of(Player player) {
        return PlayerResponse.builder()
                .playerId(player.getId())
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
