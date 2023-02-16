package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Data;

@Data
public class PlayerSearch {

    private Long memberId;
    private Long teamId;
    private Position position;
    private BaseStatus status;
    private PlayerAuthority authority;
}
