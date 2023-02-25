package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PlayerUpdateRequest {

    private Integer uniformNumber;
    private Position position;
    private BaseStatus status;
    private PlayerAuthority authority;

    @Builder
    public PlayerUpdateRequest(Integer uniformNumber, Position position, BaseStatus status, PlayerAuthority authority) {
        this.uniformNumber = uniformNumber;
        this.position = position;
        this.status = status;
        this.authority = authority;
    }
}
