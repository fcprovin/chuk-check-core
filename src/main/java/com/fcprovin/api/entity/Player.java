package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Player extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private Long id;

    private int uniformNumber;

    @Enumerated(STRING)
    private Position position;

    @Enumerated(STRING)
    private BaseStatus status;

    @Enumerated(STRING)
    private PlayerAuthority authority;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

}
