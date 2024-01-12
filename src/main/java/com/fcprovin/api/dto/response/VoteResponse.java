package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcprovin.api.entity.Vote;
import com.fcprovin.api.entity.VoteStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class VoteResponse {

    private final Long voteId;
    private final VoteStatus status;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final PlayerResponse player;
    private final MatchResponse match;

    public static VoteResponse of(Vote vote) {
        return VoteResponse.builder()
                .voteId(vote.getId())
                .status(vote.getStatus())
                .createdDate(vote.getCreatedDate())
                .updatedDate(vote.getUpdatedDate())
                .player(PlayerResponse.of(vote.getPlayer()))
                .match(MatchResponse.of(vote.getMatch()))
                .build();
    }
}
