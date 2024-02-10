package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Stadium;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class StadiumCreateRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    private double latitude;

    private double longitude;

    @Builder
    public StadiumCreateRequest(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Stadium toEntity() {
        return Stadium.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
