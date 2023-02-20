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

    @Builder
    public Vote(VoteStatus status, Player player, Match match) {
        this.status = status;
        this.player = player;

        setMatch(match);
    }

    private void setMatch(Match match) {
        this.match = match;
        this.match.getVotes().add(this);
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }
}
