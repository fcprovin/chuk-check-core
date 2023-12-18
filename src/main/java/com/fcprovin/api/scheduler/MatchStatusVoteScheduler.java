package com.fcprovin.api.scheduler;

import com.fcprovin.api.dto.request.create.VoteCreateRequest;
import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.Match;
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

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static com.fcprovin.api.entity.VoteStatus.WAIT;

@Slf4j
@Component
@Profile("!local")
@RequiredArgsConstructor
public class MatchStatusVoteScheduler {

    private final PlayerService playerService;
    private final MatchService matchService;
    private final VoteService voteService;

    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void doSchedule() {
        matchService.findForVoteCreate()
                .stream()
                .map(this::voteStatus)
                .flatMap(this::getPlayers)
                .forEach(this::voteCreate);
        log.info(getClass().getSimpleName() + "-Scheduler {}", LocalDateTime.now());
    }

    private Match voteStatus(Match match) {
        return matchService.update(match.getId(), MatchUpdateRequest.builder()
                        .status(VOTE)
                        .build());
    }

    private void voteCreate(VoteCreateRequest request) {
        voteService.create(request);
    }

    private Stream<VoteCreateRequest> getPlayers(Match match) {
        return playerService.readSearch(PlayerSearch.builder()
                        .teamId(match.getTeam().getId())
                        .status(APPROVE)
                        .build())
                .stream().map(player -> VoteCreateRequest.builder()
                        .playerId(player.getId())
                        .matchId(match.getId())
                        .status(WAIT)
                        .build());
    }
}
