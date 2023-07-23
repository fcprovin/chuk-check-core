package com.fcprovin.api.scheduler;

import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.MatchStatus;
import com.fcprovin.api.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchStatusStartScheduler {

    private final MatchService matchService;

    @Scheduled(fixedDelay = 60000, initialDelay = 40000)
    public void doSchedule() {
        matchService.findForMatchStart()
                .forEach(this::startStatus);
        log.info(getClass().getSimpleName() + "-Scheduler {}", LocalDateTime.now());
    }

    private void startStatus(Match match) {
        matchService.update(match.getId(), MatchUpdateRequest.builder()
                        .status(MatchStatus.MATCH)
                        .build());
    }

}
