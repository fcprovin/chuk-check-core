package com.fcprovin.api.scheduler;

import com.fcprovin.api.dto.request.create.AttendCreateRequest;
import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.entity.*;
import com.fcprovin.api.service.AttendService;
import com.fcprovin.api.service.MatchService;
import com.fcprovin.api.service.PlayerService;
import com.fcprovin.api.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.fcprovin.api.entity.AttendStatus.ABSENT;
import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.VoteStatus.FALSE;
import static com.fcprovin.api.entity.VoteStatus.TRUE;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MatchStatusAttendSchedulerTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @Autowired
    VoteService voteService;

    @Autowired
    AttendService attendService;

    @Test
    void getVotesToAttendCreate() {
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

        Vote vote1 = new Vote(TRUE, player1, match);
        Vote vote2 = new Vote(FALSE, player2, match);

        em.persist(vote1);
        em.persist(vote2);

        //when
        List<Attend> results = voteService.readAll(VoteSearch.builder()
                        .status(TRUE)
                        .matchId(match.getId())
                        .build()).stream()
                .map(vote -> AttendCreateRequest.builder()
                        .playerId(vote.getPlayer().getId())
                        .matchId(match.getId())
                        .status(ABSENT)
                        .build())
                .map(attendService::create)
                .collect(toList());

        assertThat(results).extracting("match").containsOnly(match);
        assertThat(results).extracting("player").containsOnly(player1);
        assertThat(results.size()).isEqualTo(1);
    }
}