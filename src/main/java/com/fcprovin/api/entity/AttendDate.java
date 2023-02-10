package com.fcprovin.api.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AttendDate {

    @Column(name = "attend_deadline_date")
    private LocalDateTime deadlineDate;

    @Builder
    public AttendDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
