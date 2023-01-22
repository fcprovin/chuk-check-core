package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Region extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "region_id")
    private Long id;

    private String country;

    private String city;
}
