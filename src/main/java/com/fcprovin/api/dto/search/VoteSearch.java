package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.VoteStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteSearch {

    private Long playerId;
    private Long matchId;
    private VoteStatus status;
}
