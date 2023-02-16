package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class MatchResponse {

    private final Long id;
    private final String otherTeamName;
    private final boolean isHome;
    private final String notice;
    private final MatchStatus status;
    private final MatchDate matchDate;
    private final VoteDate voteDate;
    private final AttendDate attendDate;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static MatchResponse of(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .otherTeamName(match.getOtherTeamName())
                .isHome(match.isHome())
                .notice(match.getNotice())
                .status(match.getStatus())
                .matchDate(match.getMatchDate())
                .voteDate(match.getVoteDate())
                .attendDate(match.getAttendDate())
                .createdDate(match.getCreatedDate())
                .updatedDate(match.getUpdatedDate())
                .build();
    }
}
