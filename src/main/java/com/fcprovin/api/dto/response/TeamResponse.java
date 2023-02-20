package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Team;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class TeamResponse {

    private final Long id;
    private final String name;
    private final BaseStatus status;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final RegionResponse region;

    public static TeamResponse of(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .status(team.getStatus())
                .createdDate(team.getCreatedDate())
                .updatedDate(team.getUpdatedDate())
                .region(RegionResponse.of(team.getRegion()))
                .build();
    }
}
