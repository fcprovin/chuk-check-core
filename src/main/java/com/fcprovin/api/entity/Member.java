package com.fcprovin.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
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

    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "sns_id")
    private Sns sns;

    @OneToMany(mappedBy = "member")
    private final List<Player> players = new ArrayList<>();

    public Member(String name) {
        this(name, null, null, null);
    }

    public Member(String name, Sns sns) {
        this(name, null, null, sns);
    }

    @Builder
    public Member(String name, String email, LocalDate birthDate, Sns sns) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.sns = sns;
    }
}
