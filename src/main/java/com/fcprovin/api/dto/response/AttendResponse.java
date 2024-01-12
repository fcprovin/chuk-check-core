package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcprovin.api.entity.Attend;
import com.fcprovin.api.entity.AttendStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class AttendResponse {

    private final Long attendId;
    private final AttendStatus status;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final PlayerResponse player;
    private final MatchResponse match;

    public static AttendResponse of(Attend attend) {
        return AttendResponse.builder()
                .attendId(attend.getId())
                .status(attend.getStatus())
                .createdDate(attend.getCreatedDate())
                .updatedDate(attend.getUpdatedDate())
                .player(PlayerResponse.of(attend.getPlayer()))
                .match(MatchResponse.of(attend.getMatch()))
                .build();
    }
}
