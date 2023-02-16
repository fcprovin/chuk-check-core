package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Data;

@Data
public class PlayerUpdateRequest {

    private Integer uniformNumber;
    private Position position;
    private BaseStatus status;
    private PlayerAuthority authority;
}
