package com.fcprovin.api.repository;

import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(false)
class AttendRepositoryTest {

    @Autowired
    AttendRepository attendRepository;

    @Autowired
    EntityManager em;

    @Test
    void saveTest() {
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
        Attend attend = new Attend(AttendStatus.ATTEND, player, match);
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
    void statusUpdateTest() {
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
        Attend attend = new Attend(AttendStatus.ATTEND, player, match);

        attendRepository.save(attend);
        attend.setStatus(AttendStatus.LATE);

        em.flush();
        em.clear();

        Attend findAttend = attendRepository.findById(attend.getId()).orElseThrow();

        //then
        assertThat(findAttend.getStatus()).isEqualTo(AttendStatus.LATE);
    }
}