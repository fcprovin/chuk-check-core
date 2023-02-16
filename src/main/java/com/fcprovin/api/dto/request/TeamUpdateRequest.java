package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.BaseStatus;
import lombok.Data;

@Data
public class TeamUpdateRequest {

    private BaseStatus status;
}
