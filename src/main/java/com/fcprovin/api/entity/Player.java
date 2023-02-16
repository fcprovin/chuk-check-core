package com.fcprovin.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static com.fcprovin.api.entity.PlayerAuthority.GENERAL;
import static com.fcprovin.api.entity.PlayerAuthority.LEADER;
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

    public Player(Member member, Team team) {
        this(member, team, GENERAL);
    }

    @Builder
    public Player(Member member, Team team, PlayerAuthority authority) {
        this.authority = authority;
        this.status = LEADER.equals(authority) ? APPROVE : WAIT;

        setMember(member);
        setTeam(team);
    }

    private void setMember(Member member) {
        this.member = member;
        this.member.getPlayers().add(this);
    }

    private void setTeam(Team team) {
        this.team = team;
        this.team.getPlayers().add(this);
    }

    public void setUniformNumber(int uniformNumber) {
        this.uniformNumber = uniformNumber;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }

    public void setAuthority(PlayerAuthority authority) {
        this.authority = authority;
    }
}
