package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Vote;
import com.fcprovin.api.entity.VoteStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class VoteCreateRequest {

    @NotEmpty
    private Long playerId;

    @NotEmpty
    private Long matchId;

    @NotEmpty
    private VoteStatus status;

    public Vote toEntity(Player player, Match match) {
        return Vote.builder()
                .player(player)
                .match(match)
                .status(status)
                .build();
    }
}
