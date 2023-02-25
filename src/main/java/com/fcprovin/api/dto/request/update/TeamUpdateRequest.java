package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.BaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class TeamUpdateRequest {

    private BaseStatus status;

    @Builder
    public TeamUpdateRequest(BaseStatus status) {
        this.status = status;
    }
}
