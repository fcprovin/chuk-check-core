package com.fcprovin.api.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Match extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;

    private String otherTeamName;

    private boolean isHome;

    private String notice;

    private MatchStatus status;

    @Embedded
    private MatchDate matchDate;

    @Embedded
    private VoteDate voteDate;

    @Embedded
    private AttendDate attendDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "match")
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "match")
    private List<Attend> attends = new ArrayList<>();
}
