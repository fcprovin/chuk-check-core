package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Region;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RegionCreateRequest {

    @NotEmpty
    private final String country;

    @NotEmpty
    private final String city;

    public Region toEntity() {
        return Region.builder()
                .country(country)
                .city(city)
                .build();
    }
}
