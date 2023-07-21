package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MatchSearch;
import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.fcprovin.api.entity.MatchStatus.ATTEND;
import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MatchRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MatchRepository matchRepository;

    @Test
    void save() {
    	//given
        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);

        //when
        Match saveMatch = matchRepository.save(match);
        Match findMatch = matchRepository.findById(saveMatch.getId()).get();

        //then
        assertThat(findMatch).isEqualTo(match);
        assertThat(findMatch.getId()).isEqualTo(match.getId());
        assertThat(findMatch.getOtherTeamName()).isEqualTo(match.getOtherTeamName());
        assertThat(findMatch.isHome()).isTrue();
        assertThat(findMatch.getNotice()).isEqualTo(match.getNotice());
        assertThat(findMatch.getMatchDate().getStartDate()).isEqualTo(match.getMatchDate().getStartDate());
        assertThat(findMatch.getMatchDate().getEndDate()).isEqualTo(match.getMatchDate().getEndDate());
        assertThat(findMatch.getVoteDate().getStartDate()).isEqualTo(match.getVoteDate().getStartDate());
        assertThat(findMatch.getVoteDate().getEndDate()).isEqualTo(match.getVoteDate().getEndDate());
        assertThat(findMatch.getAttendDate().getDeadlineDate()).isEqualTo(match.getAttendDate().getDeadlineDate());
        assertThat(findMatch.getTeam()).isEqualTo(match.getTeam());
        assertThat(findMatch.getStadium()).isEqualTo(match.getStadium());
    }

    @Test
    void update() {
    	//given
        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        em.persist(match);

    	//when
        match.setStatus(VOTE);
        match.setHome(false);
        match.setOtherTeamName("징기스칸");

        Match findMatch = matchRepository.findById(match.getId()).orElseThrow();

        //then
        assertThat(findMatch.getStatus()).isEqualTo(VOTE);
        assertThat(findMatch.isHome()).isFalse();
        assertThat(findMatch.getOtherTeamName()).isEqualTo("징기스칸");
    }

    @Test
    void findQueryBySearch() {
    	//given
    	MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        em.persist(match);

        MatchSearch search = MatchSearch.builder()
                .startDate(LocalDate.now().minusDays(4))
                .endDate(LocalDate.now().plusDays(4))
                .build();

        //when
        List<Match> result = matchRepository.findQueryBySearch(search);

        //then
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result).containsExactly(match);
    }

    @Test
    void findQueryById() {
    	//given
        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        em.persist(match);

    	//when
        Match findMatch = matchRepository.findQueryById(match.getId()).get();

        //then
        assertThat(findMatch.getTeam().getName()).isEqualTo(team.getName());
        assertThat(findMatch.getStadium().getName()).isEqualTo(stadium.getName());
    }

    @Test
    void findQueryVoteStartByStatus() {
        //given
        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(LocalDateTime.of(LocalDate.of(2022, 7, 1), LocalTime.now()), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        em.persist(match);

        //when
        List<Match> matches = matchRepository.findQueryVoteStartByStatus(MatchStatus.CREATE);

        //then
        assertThat(matches).containsExactly(match);
    }

    @Test
    void findQueryVoteEndByStatus() {
        //given
        MatchDate matchDate = new MatchDate(now(), now());
        VoteDate voteDate = new VoteDate(LocalDateTime.of(LocalDate.of(2022, 6, 20), LocalTime.now()),
                LocalDateTime.of(LocalDate.of(2022, 7, 1), LocalTime.now()));
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        match.setStatus(VOTE);
        em.persist(match);

        //when
        List<Match> matches = matchRepository.findQueryVoteEndByStatus(VOTE);

        //then
        assertThat(matches).containsExactly(match);
    }

    @Test
    void findQueryMatchStartByStatus() {
    	//given
        MatchDate matchDate = new MatchDate(LocalDateTime.of(LocalDate.of(2022, 7, 1), LocalTime.now()), now());
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        match.setStatus(ATTEND);
        em.persist(match);

    	//when
        List<Match> matches = matchRepository.findQueryMatchStartByStatus(MatchStatus.ATTEND);

        //then
        assertThat(matches).containsExactly(match);
    }

    @Test
    void findQueryMatchEndByStatus() {
    	//given
        MatchDate matchDate = new MatchDate(now(), LocalDateTime.of(LocalDate.of(2022, 7, 1), LocalTime.now()));
        VoteDate voteDate = new VoteDate(now(), now());
        AttendDate attendDate = new AttendDate(now());

        Region region = new Region("경기도", "성남시");
        Team team = new Team("프로빈", region);
        Stadium stadium = new Stadium("황송", "성남시 중원구", 32.12, 128.12);

        em.persist(region);
        em.persist(team);
        em.persist(stadium);

        Match match = new Match("블랑", true, "공지", matchDate, voteDate, attendDate, team, stadium);
        match.setStatus(ATTEND);
        em.persist(match);

    	//when
        List<Match> matches = matchRepository.findQueryMatchEndByStatus(MatchStatus.ATTEND);

        //then
        assertThat(matches).containsExactly(match);
    }
}