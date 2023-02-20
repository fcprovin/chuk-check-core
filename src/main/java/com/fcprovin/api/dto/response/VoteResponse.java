package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Vote;
import com.fcprovin.api.entity.VoteStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VoteResponse {

    private final Long id;
    private final VoteStatus status;
    private final PlayerResponse player;
    private final MatchResponse match;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static VoteResponse of(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .status(vote.getStatus())
                .player(PlayerResponse.of(vote.getPlayer()))
                .match(MatchResponse.of(vote.getMatch()))
                .createdDate(vote.getCreatedDate())
                .updatedDate(vote.getUpdatedDate())
                .build();
    }
}
