package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Region;
import com.fcprovin.api.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class TeamCreateRequest {

    @NotEmpty
    private Long regionId;

    @NotEmpty
    private String name;

    public Team toEntity(Region region) {
        return Team.builder()
                .name(name)
                .region(region)
                .build();
    }
}
