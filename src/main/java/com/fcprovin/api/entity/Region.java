package com.fcprovin.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
