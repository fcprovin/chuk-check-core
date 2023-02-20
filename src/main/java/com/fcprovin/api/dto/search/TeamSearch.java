package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.BaseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamSearch {

    private Long regionId;
    private String name;
    private BaseStatus status;
}
