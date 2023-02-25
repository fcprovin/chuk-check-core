package com.fcprovin.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class VoteDate {

    @Column(name = "vote_start_date")
    private LocalDateTime startDate;

    @Column(name = "vote_end_date")
    private LocalDateTime endDate;

    @Builder
    public VoteDate(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static VoteDate of(LocalDateTime startDate, LocalDateTime endDate) {
        return new VoteDate(startDate, endDate);
    }
}
