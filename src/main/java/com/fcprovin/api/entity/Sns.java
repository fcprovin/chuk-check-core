package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Getter
@Entity
public class Sns extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "sns_id")
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Enumerated(STRING)
    private SnsType type;

    @OneToOne(mappedBy = "sns")
    private Member member;
}
