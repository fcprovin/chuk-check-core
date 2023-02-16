package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class StadiumRequest {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String address;

    private double latitude;

    private double longitude;

    public StadiumRequest(String name, String address) {
        this.name = name;
        this.address = address;
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
