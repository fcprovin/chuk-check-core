package com.fcprovin.api.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class VoteDate {

    @Column(name = "vote_start_date")
    private LocalDateTime startDate;

    @Column(name = "vote_end_date")
    private LocalDateTime endDate;
}
