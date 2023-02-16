package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RegionRequest {

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
