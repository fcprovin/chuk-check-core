package com.fcprovin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Team extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @Enumerated(STRING)
    private BaseStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Match> matches = new ArrayList<>();

    @Builder
    public Team(String name, Region region, Player player) {
        this.name = name;
        this.region = region;
        this.status = WAIT;
    }

    public void changeStatus(BaseStatus status) {
        this.status = status;
    }
}
