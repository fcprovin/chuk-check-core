package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.TeamCreateRequest;
import com.fcprovin.api.entity.Team;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class TeamServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    TeamService teamService;

    @Test
    void validateName() {
    	//given
        em.persist(new Team("teamA"));

    	//then
        assertThatThrownBy(() -> teamService.create(TeamCreateRequest.builder()
                .name("teamA")
                .build()), "Already team name").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> teamService.read(Long.MIN_VALUE), "Not exist team")
                .isInstanceOf(IllegalArgumentException.class);
    }
}