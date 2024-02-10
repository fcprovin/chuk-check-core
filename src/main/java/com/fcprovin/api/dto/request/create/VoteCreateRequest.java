package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Vote;
import com.fcprovin.api.entity.VoteStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class VoteCreateRequest {

    @NotEmpty
    private Long playerId;

    @NotEmpty
    private Long matchId;

    @NotEmpty
    private VoteStatus status;

    @Builder
    public VoteCreateRequest(Long playerId, Long matchId, VoteStatus status) {
        this.playerId = playerId;
        this.matchId = matchId;
        this.status = status;
    }

    public Vote toEntity(Player player, Match match) {
        return Vote.builder()
                .player(player)
                .match(match)
                .status(status)
                .build();
    }
}
