package com.fcprovin.api.repository;

import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.fcprovin.api.entity.VoteStatus.TRUE;
import static com.fcprovin.api.entity.VoteStatus.WAIT;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(value = false)
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

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
        Vote vote = new Vote(WAIT, player, match);
        Vote saveVote = voteRepository.save(vote);
        Vote findVote = voteRepository.findById(saveVote.getId()).orElseThrow();

        //then
        assertThat(findVote).isEqualTo(vote);
        assertThat(findVote.getId()).isEqualTo(vote.getId());
        assertThat(findVote.getPlayer()).isEqualTo(vote.getPlayer());
        assertThat(findVote.getStatus()).isEqualTo(vote.getStatus());
        assertThat(findVote.getMatch()).isEqualTo(vote.getMatch());
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
        Vote vote = new Vote(WAIT, player, match);
        voteRepository.save(vote);

        vote.setStatus(TRUE);

        em.flush();
        em.clear();

        Vote findVote = voteRepository.findById(vote.getId()).orElseThrow();

        //then
        assertThat(findVote.getStatus()).isEqualTo(TRUE);
    }
}