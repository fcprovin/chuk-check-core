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
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static VoteResponse of(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .status(vote.getStatus())
                .createdDate(vote.getCreatedDate())
                .updatedDate(vote.getUpdatedDate())
                .build();
    }
}
