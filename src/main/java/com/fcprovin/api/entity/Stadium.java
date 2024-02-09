package com.fcprovin.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Stadium extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "stadium_id")
    private Long id;

    private String name;

    private String address;

    private double latitude;

    private double longitude;

    @Builder
    public Stadium(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Stadium(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
