package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.AttendSearch;
import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.fcprovin.api.entity.AttendStatus.ATTEND;
import static com.fcprovin.api.entity.AttendStatus.LATE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AttendRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    AttendRepository attendRepository;

    @Test
    void save() {
    	//given
        Member member = new Member("memberA");
        Team team = new Team("teamA");

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);
        Match match = new Match("상대", new MatchDate(now(), now()), team);

        em.persist(player);
        em.persist(match);

        //when
        Attend attend = new Attend(ATTEND, player, match);
        Attend saveAttend = attendRepository.save(attend);
        Attend findAttend = attendRepository.findById(saveAttend.getId()).orElseThrow();

        //then
        assertThat(findAttend).isEqualTo(attend);
        assertThat(findAttend.getId()).isEqualTo(attend.getId());
        assertThat(findAttend.getStatus()).isEqualTo(attend.getStatus());
        assertThat(findAttend.getPlayer()).isEqualTo(attend.getPlayer());
        assertThat(findAttend.getMatch()).isEqualTo(attend.getMatch());
    }

    @Test
    void update() {
        //given
        Member member = new Member("memberA");
        Team team = new Team("teamA");

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);
        Match match = new Match("상대", new MatchDate(now(), now()), team);

        em.persist(player);
        em.persist(match);

        Attend attend = new Attend(ATTEND, player, match);

        em.persist(attend);

        //when
        attend.setStatus(LATE);
        Attend findAttend = attendRepository.findById(attend.getId()).get();

        //then
        assertThat(findAttend.getStatus()).isEqualTo(LATE);
    }

    @Test
    void findQueryBySearch() {
    	//given
        Member member = new Member("memberA");
        Team team = new Team("teamA");

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);
        Match match = new Match("상대", new MatchDate(now(), now()), team);

        em.persist(player);
        em.persist(match);

        Attend attend = new Attend(ATTEND, player, match);

        em.persist(attend);

        AttendSearch search = AttendSearch.builder()
                .status(ATTEND)
                .build();
        //when
        List<Attend> result = attendRepository.findQueryBySearch(search);

        //then
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result).containsExactly(attend);
    }

    @Test
    void findQueryById() {
    	//given
    	Member member = new Member("memberA");
        Team team = new Team("teamA");

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);
        Match match = new Match("상대", new MatchDate(now(), now()), team);

        em.persist(player);
        em.persist(match);

        Attend attend = new Attend(ATTEND, player, match);

        em.persist(attend);

    	//when
        Attend findAttend = attendRepository.findQueryById(attend.getId()).get();

        //then
        assertThat(findAttend.getPlayer().getId()).isEqualTo(player.getId());
        assertThat(findAttend.getMatch().getOtherTeamName()).isEqualTo(match.getOtherTeamName());
    }
}