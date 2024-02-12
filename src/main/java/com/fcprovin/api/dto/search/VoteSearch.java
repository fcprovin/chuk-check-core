package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteSearch {

    private Long playerId;
    private Long matchId;
    private VoteStatus status;
}
