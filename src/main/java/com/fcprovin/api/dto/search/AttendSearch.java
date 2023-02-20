package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.AttendStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendSearch {

    private Long playerId;
    private Long matchId;
    private AttendStatus status;
}
