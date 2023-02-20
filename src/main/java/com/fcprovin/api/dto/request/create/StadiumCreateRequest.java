package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Stadium;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class StadiumCreateRequest {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String address;

    private double latitude;

    private double longitude;

    public Stadium toEntity() {
        return Stadium.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
