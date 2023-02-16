package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Stadium;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StadiumResponse {

    private final Long id;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static StadiumResponse of(Stadium stadium) {
        return StadiumResponse.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .address(stadium.getAddress())
                .latitude(stadium.getLatitude())
                .longitude(stadium.getLongitude())
                .createdDate(stadium.getCreatedDate())
                .updatedDate(stadium.getUpdatedDate())
                .build();
    }
}
