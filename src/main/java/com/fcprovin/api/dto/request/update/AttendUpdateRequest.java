package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.AttendStatus;
import lombok.Data;

@Data
public class AttendUpdateRequest {

    private AttendStatus status;
}
