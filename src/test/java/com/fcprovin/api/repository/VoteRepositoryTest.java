package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fcprovin.api.entity.VoteStatus.TRUE;
import static com.fcprovin.api.entity.VoteStatus.WAIT;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class VoteRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    VoteRepository voteRepository;

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

        Vote vote = new Vote(WAIT, player, match);
        em.persist(vote);

        //when
        vote.setStatus(TRUE);
        Vote findVote = voteRepository.findById(vote.getId()).orElseThrow();

        //then
        assertThat(findVote.getStatus()).isEqualTo(TRUE);
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

        Vote vote = new Vote(WAIT, player, match);
        em.persist(vote);

        VoteSearch search = VoteSearch.builder().status(WAIT).build();

        //when
        List<Vote> result = voteRepository.findQueryBySearch(search);

        //then
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result).containsExactly(vote);
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

        Vote vote = new Vote(WAIT, player, match);
        em.persist(vote);

    	//when
        Vote findVote = voteRepository.findQueryById(vote.getId()).get();

        //then
        assertThat(findVote.getPlayer().getId()).isEqualTo(player.getId());
        assertThat(findVote.getMatch().getOtherTeamName()).isEqualTo(match.getOtherTeamName());
    }
}