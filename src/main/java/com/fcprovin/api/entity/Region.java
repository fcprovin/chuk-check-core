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
public class Region extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "region_id")
    private Long id;

    private String country;

    private String city;

    @Builder
    public Region(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
