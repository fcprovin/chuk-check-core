package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Vote extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;

    @Enumerated(STRING)
    private VoteStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
}
