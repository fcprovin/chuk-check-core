package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Team;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@SuperBuilder
public class TeamDetailResponse extends TeamResponse {

    private final RegionResponse region;
    private final List<PlayerResponse> players;
    private final List<MatchResponse> matches;

    public static TeamDetailResponse of(Team team) {
        return TeamDetailResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .status(team.getStatus())
                .createdDate(team.getCreatedDate())
                .updatedDate(team.getUpdatedDate())
                .region(RegionResponse.of(team.getRegion()))
                .players(team.getPlayers().stream().map(PlayerResponse::of).collect(toList()))
                .matches(team.getMatches().stream().map(MatchResponse::of).collect(toList()))
                .build();
    }
}
