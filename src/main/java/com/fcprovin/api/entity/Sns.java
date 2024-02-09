package com.fcprovin.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Sns extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "sns_id")
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Enumerated(STRING)
    private SnsType type;

    @Builder
    public Sns(String uuid, SnsType type) {
        this.uuid = uuid;
        this.type = type;
    }
}
