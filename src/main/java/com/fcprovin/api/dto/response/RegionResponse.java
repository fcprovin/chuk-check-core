package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Region;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RegionResponse {

    private final Long regionId;
    private final String country;
    private final String city;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static RegionResponse of(Region region) {
        return RegionResponse.builder()
                .regionId(region.getId())
                .country(region.getCountry())
                .city(region.getCity())
                .createdDate(region.getCreatedDate())
                .updatedDate(region.getUpdatedDate())
                .build();
    }
}
