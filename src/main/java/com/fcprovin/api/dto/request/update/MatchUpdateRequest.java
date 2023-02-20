package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.MatchStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchUpdateRequest {

    private String otherTeamName;
    private Boolean isHome;
    private String notice;
    private MatchStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime voteStartDate;
    private LocalDateTime voteEndDate;
    private LocalDateTime attendDeadLineDate;
}
