package com.fcprovin.api.entity;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class MatchDate {

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
