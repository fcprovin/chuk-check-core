package com.fcprovin.api.entity;

import com.fcprovin.api.dto.jwt.JwtRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Token extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    @Enumerated(STRING)
    private JwtRole role;

    private Long subject;

    private String refreshToken;

    private LocalDateTime expireDate;

    @Builder
    public Token(JwtRole role, Long subject, String refreshToken, LocalDateTime expireDate) {
        this.role = role;
        this.subject = subject;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }
}
