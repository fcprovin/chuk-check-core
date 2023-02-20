package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MatchSearch {

    private Long teamId;
    private Long stadiumId;
    private MatchStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
