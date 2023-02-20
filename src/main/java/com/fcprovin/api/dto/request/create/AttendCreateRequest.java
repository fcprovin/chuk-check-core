package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Attend;
import com.fcprovin.api.entity.AttendStatus;
import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.Player;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AttendCreateRequest {

    @NotEmpty
    private Long playerId;

    @NotEmpty
    private Long matchId;

    @NotEmpty
    private AttendStatus status;

    public Attend toEntity(Player player, Match match) {
        return Attend.builder()
                .player(player)
                .match(match)
                .status(status)
                .build();
    }
}
