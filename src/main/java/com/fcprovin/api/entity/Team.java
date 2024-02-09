package com.fcprovin.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
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

    public Team(String name) {
        this.name = name;
    }

    @Builder
    public Team(String name, Region region) {
        this.name = name;
        this.region = region;
        this.status = WAIT;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }
}
