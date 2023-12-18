package com.fcprovin.api.scheduler;

import com.fcprovin.api.dto.request.create.AttendCreateRequest;
import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.MatchStatus;
import com.fcprovin.api.service.AttendService;
import com.fcprovin.api.service.MatchService;
import com.fcprovin.api.service.PlayerService;
import com.fcprovin.api.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.fcprovin.api.entity.AttendStatus.ABSENT;
import static com.fcprovin.api.entity.VoteStatus.TRUE;

@Slf4j
@Component
@Profile("!local")
@RequiredArgsConstructor
public class MatchStatusAttendScheduler {

    private final MatchService matchService;
    private final VoteService voteService;
    private final AttendService attendService;

    @Scheduled(fixedDelay = 60000, initialDelay = 20000)
    public void doSchedule() {
        matchService.findForAttendCreate()
                .stream()
                .map(this::attendStatus)
                .flatMap(this::getPlayers)
                .forEach(this::attendCreate);
        log.info(getClass().getSimpleName() + "-Scheduler {}", LocalDateTime.now());
    }

    private Match attendStatus(Match match) {
        return matchService.update(match.getId(), MatchUpdateRequest.builder()
                .status(MatchStatus.ATTEND)
                .build());
    }

    private void attendCreate(AttendCreateRequest request) {
        attendService.create(request);
    }

    private Stream<AttendCreateRequest> getPlayers(Match match) {
        return voteService.readAll(VoteSearch.builder()
                        .matchId(match.getId())
                        .status(TRUE)
                        .build())
                .stream().map(vote -> AttendCreateRequest.builder()
                        .playerId(vote.getPlayer().getId())
                        .matchId(match.getId())
                        .status(ABSENT)
                        .build());
    }
}
