package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Attend extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "attend_id")
    private Long id;

    @Enumerated(STRING)
    private AttendStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
}
