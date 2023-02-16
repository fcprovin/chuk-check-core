package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Attend;
import com.fcprovin.api.entity.AttendStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttendResponse {

    private final Long id;
    private final AttendStatus status;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static AttendResponse of(Attend attend) {
        return AttendResponse.builder()
                .id(attend.getId())
                .status(attend.getStatus())
                .createdDate(attend.getCreatedDate())
                .updatedDate(attend.getUpdatedDate())
                .build();
    }
}
