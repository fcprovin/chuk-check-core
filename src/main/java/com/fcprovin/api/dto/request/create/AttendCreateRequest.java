package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Attend;
import com.fcprovin.api.entity.AttendStatus;
import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.Player;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AttendCreateRequest {

    @NotEmpty
    private Long playerId;

    @NotEmpty
    private Long matchId;

    @NotEmpty
    private AttendStatus status;

    @Builder
    public AttendCreateRequest(Long playerId, Long matchId, AttendStatus status) {
        this.playerId = playerId;
        this.matchId = matchId;
        this.status = status;
    }

    public Attend toEntity(Player player, Match match) {
        return Attend.builder()
                .player(player)
                .match(match)
                .status(status)
                .build();
    }
}
