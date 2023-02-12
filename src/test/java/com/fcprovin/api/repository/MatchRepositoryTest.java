package com.fcprovin.api.repository;

import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(false)
class MatchRepositoryTest {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    EntityManager em;

    @Test
    void saveTest() {
    	//given
        MatchDate matchDate = new MatchDate(LocalDateTime.now(), LocalDateTime.now());
        VoteDate voteDate = new VoteDate(LocalDateTime.now(), LocalDateTime.now());
        AttendDate attendDate = new AttendDate(LocalDateTime.now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);

        //when
        Match saveMatch = matchRepository.save(match);
        Match findMatch = matchRepository.findById(saveMatch.getId()).orElseThrow();

        //then
        assertThat(findMatch).isEqualTo(match);
        assertThat(findMatch.getId()).isEqualTo(match.getId());
        assertThat(findMatch.getOtherTeamName()).isEqualTo(match.getOtherTeamName());
        assertThat(findMatch.isHome()).isTrue();
        assertThat(findMatch.getNotice()).isEqualTo(match.getNotice());
        assertThat(findMatch.getMatchDate()).isEqualTo(match.getMatchDate());
        assertThat(findMatch.getVoteDate()).isEqualTo(match.getVoteDate());
        assertThat(findMatch.getAttendDate()).isEqualTo(match.getAttendDate());
        assertThat(findMatch.getTeam()).isEqualTo(match.getTeam());
        assertThat(findMatch.getStadium()).isEqualTo(match.getStadium());
    }

    @Test
    void updateTest() {
    	//given
        MatchDate matchDate = new MatchDate(LocalDateTime.now(), LocalDateTime.now());
        VoteDate voteDate = new VoteDate(LocalDateTime.now(), LocalDateTime.now());
        AttendDate attendDate = new AttendDate(LocalDateTime.now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        matchRepository.save(match);

    	//when
        match.setStatus(VOTE);
        match.setHome(false);
        match.setOtherTeamName("징기스칸");

        em.flush();
        em.clear();

        Match findMatch = matchRepository.findById(match.getId()).orElseThrow();

        //then
        assertThat(findMatch.getStatus()).isEqualTo(VOTE);
        assertThat(findMatch.isHome()).isFalse();
        assertThat(findMatch.getOtherTeamName()).isEqualTo("징기스칸");
    }
}