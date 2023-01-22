package com.fcprovin.api.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class AttendDate {

    @Column(name = "attend_deadline_date")
    private LocalDateTime deadlineDate;
}
