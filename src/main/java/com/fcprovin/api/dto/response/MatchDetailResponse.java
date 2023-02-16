package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Match;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@SuperBuilder
public class MatchDetailResponse extends MatchResponse {

    private final TeamResponse team;
    private final StadiumResponse stadium;
    private final List<VoteResponse> votes;
    private final List<AttendResponse> attends;

    public static MatchDetailResponse of(Match match) {
        return MatchDetailResponse.builder()
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
                .team(TeamResponse.of(match.getTeam()))
                .stadium(StadiumResponse.of(match.getStadium()))
                .votes(match.getVotes().stream().map(VoteResponse::of).collect(toList()))
                .attends(match.getAttends().stream().map(AttendResponse::of).collect(toList()))
                .build();
    }
}
