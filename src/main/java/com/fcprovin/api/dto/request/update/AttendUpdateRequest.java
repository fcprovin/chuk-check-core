package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.AttendStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AttendUpdateRequest {

    private AttendStatus status;

    public AttendUpdateRequest(AttendStatus status) {
        this.status = status;
    }
}
