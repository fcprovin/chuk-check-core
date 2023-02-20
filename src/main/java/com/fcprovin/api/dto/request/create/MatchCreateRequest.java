package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
public class MatchCreateRequest {

    @NotEmpty
    private final Long teamId;

    @NotEmpty
    private final Long stadiumId;

    private final String otherTeamName;

    @NotEmpty
    private final boolean isHome;

    private final String notice;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime voteStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime voteEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime attendDeadlineDate;

    public Match toEntity(Team team, Stadium stadium) {
        return Match.builder()
                .team(team)
                .stadium(stadium)
                .otherTeamName(otherTeamName)
                .isHome(isHome)
                .notice(notice)
                .matchDate(MatchDate.of(startDate, endDate))
                .voteDate(VoteDate.of(voteStartDate, voteEndDate))
                .attendDate(AttendDate.of(attendDeadlineDate))
                .build();
    }
}
