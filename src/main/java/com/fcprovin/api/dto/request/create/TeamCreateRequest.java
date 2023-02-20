package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Region;
import com.fcprovin.api.entity.Team;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class TeamCreateRequest {

    @NotEmpty
    private Long regionId;

    @NotEmpty
    private String name;

    public Team toEntity(Region region) {
        return Team.builder()
                .region(region)
                .name(name)
                .build();
    }
}
