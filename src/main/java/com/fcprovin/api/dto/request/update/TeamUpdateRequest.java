package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.BaseStatus;
import lombok.Data;

@Data
public class TeamUpdateRequest {

    private BaseStatus status;
}
