package com.fcprovin.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public Attend(AttendStatus status, Player player, Match match) {
        this.status = status;
        this.player = player;

        setMatch(match);
    }

    private void setMatch(Match match) {
        this.match = match;
        this.match.getAttends().add(this);
    }

    public void setStatus(AttendStatus status) {
        this.status = status;
    }
}
