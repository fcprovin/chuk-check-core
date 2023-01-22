package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Stadium extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "stadium_id")
    private Long id;

    private String name;

    private String address;

    private double latitude;

    private double longitude;
}
