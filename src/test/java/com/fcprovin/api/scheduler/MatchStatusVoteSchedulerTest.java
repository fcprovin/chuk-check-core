package com.fcprovin.api.scheduler;

import com.fcprovin.api.dto.request.create.VoteCreateRequest;
import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.*;
import com.fcprovin.api.service.MatchService;
import com.fcprovin.api.service.PlayerService;
import com.fcprovin.api.service.VoteService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.VoteStatus.WAIT;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MatchStatusVoteSchedulerTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @Autowired
    VoteService voteService;

    @Test
    void getPlayersToVoteCreate() {
        //given
        Member member1 = new Member("테스트1");
        Member member2 = new Member("테스트2");

        em.persist(member1);
        em.persist(member2);

        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(LocalDateTime.of(LocalDate.of(2022, 7, 1), LocalTime.now()), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Player player1 = new Player(member1, team);
        Player player2 = new Player(member2, team);

        player1.setStatus(APPROVE);
        player2.setStatus(APPROVE);

        em.persist(player1);
        em.persist(player2);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        em.persist(match);

        //when
        List<Vote> results = playerService.readSearch(PlayerSearch.builder()
                        .teamId(match.getTeam().getId())
                        .status(APPROVE)
                        .build())
                .stream().map(player -> VoteCreateRequest.builder()
                        .playerId(player.getId())
                        .matchId(match.getId())
                        .status(WAIT)
                        .build())
                .map(voteService::create)
                .collect(toList());

        assertThat(results).extracting("match").containsOnly(match);
        assertThat(results).extracting("player").contains(player1, player2);
        assertThat(results.size()).isEqualTo(2);
    }
}