package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcprovin.api.entity.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class MatchResponse {

    private final Long id;
    private final String otherTeamName;
    private final Boolean home;
    private final String notice;
    private final MatchStatus status;
    private final MatchDate matchDate;
    private final VoteDate voteDate;
    private final AttendDate attendDate;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final TeamResponse team;
    private final StadiumResponse stadium;

    public static MatchResponse of(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .otherTeamName(match.getOtherTeamName())
                .home(match.isHome())
                .notice(match.getNotice())
                .status(match.getStatus())
                .matchDate(match.getMatchDate())
                .voteDate(match.getVoteDate())
                .attendDate(match.getAttendDate())
                .createdDate(match.getCreatedDate())
                .updatedDate(match.getUpdatedDate())
                .team(TeamResponse.of(match.getTeam()))
                .stadium(StadiumResponse.of(match.getStadium()))
                .build();
    }
}
