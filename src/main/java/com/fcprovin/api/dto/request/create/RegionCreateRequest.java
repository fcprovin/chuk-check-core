package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RegionCreateRequest {

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @Builder
    public RegionCreateRequest(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public Region toEntity() {
        return Region.builder()
                .country(country)
                .city(city)
                .build();
    }
}
