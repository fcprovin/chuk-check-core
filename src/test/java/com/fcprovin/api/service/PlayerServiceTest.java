package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.PlayerCreateRequest;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
}