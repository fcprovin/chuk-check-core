package com.fcprovin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static com.fcprovin.api.entity.PlayerAuthority.MANAGER;
import static com.fcprovin.api.entity.PlayerAuthority.MEMBER;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public Player(Member member, Team team, PlayerAuthority authority) {
        this.member = member;
        this.team = team;
        this.authority = authority;
        this.status = WAIT;

        this.member.getPlayers().add(this);
        this.team.getPlayers().add(this);
    }

    public void changeInfo(int uniformNumber, Position position) {
        this.uniformNumber = uniformNumber;
        this.position = position;
    }

    public void changeStatus(BaseStatus status) {
        this.status = status;
    }

    public void changeAuthority(PlayerAuthority authority) {
        this.authority = authority;
    }
}
