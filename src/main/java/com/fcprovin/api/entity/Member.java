package com.fcprovin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    private LocalDateTime loginDate;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "sns_id")
    private Sns sns;

    @OneToMany(mappedBy = "member")
    private List<Player> players = new ArrayList<>();

    @Builder
    public Member(String name, String email, LocalDate birthDate, Sns sns) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.sns = sns;
    }

    public void login() {
        loginDate = LocalDateTime.now();
    }
}
