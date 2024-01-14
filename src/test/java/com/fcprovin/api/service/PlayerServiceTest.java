package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.PlayerCreateRequest;
import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.fcprovin.api.entity.PlayerAuthority.GENERAL;
import static com.fcprovin.api.entity.PlayerAuthority.LEADER;
import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class PlayerServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlayerService playerService;

    @Test
    void validate() {
        Member member = new Member("member1");
        Team team = new Team("teamA");

        em.persist(member);
        em.persist(team);

        em.persist(new Player(member, team));

    	//then
        assertThatThrownBy(() -> playerService.create(PlayerCreateRequest.builder()
                .memberId(member.getId())
                .teamId(team.getId())
                .build()), "Already player").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> playerService.read(Long.MIN_VALUE), "Not exist player")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void teams() {
    	//given
        Sns sns = new Sns("sns1", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);
        Team team2 = new Team("프로빈2", region);

        em.persist(member);
        em.persist(team);
        em.persist(team2);

        Player player = new Player(member, team, LEADER);
        Player player2 = new Player(member, team2, GENERAL);

        em.persist(player);
        em.persist(player2);

        //when
        List<Team> teams = playerService.readTeamByMemberId(member.getId());

        //then
        assertThat(teams).contains(team, team2);
    }
}