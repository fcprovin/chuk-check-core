package com.fcprovin.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Admin extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String name;

    public Admin(String name) {
        this.name = name;
    }
}
