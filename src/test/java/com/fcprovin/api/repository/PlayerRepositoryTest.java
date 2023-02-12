package com.fcprovin.api.repository;

import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static com.fcprovin.api.entity.BaseStatus.EXIT;
import static com.fcprovin.api.entity.PlayerAuthority.*;
import static com.fcprovin.api.entity.Position.GK;
import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(false)
class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    EntityManager em;

    @Test
    void saveTest() {
    	//given
        Sns sns = new Sns("sns1", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        //when
        Player player = new Player(member, team, LEADER);

        Player savePlayer = playerRepository.save(player);
        Player findPlayer = playerRepository.findById(savePlayer.getId()).orElseThrow();

        //then
        assertThat(findPlayer).isEqualTo(player);
        assertThat(findPlayer.getId()).isEqualTo(player.getId());
        assertThat(findPlayer.getMember()).isEqualTo(member);
        assertThat(findPlayer.getMember().getSns()).isEqualTo(sns);
        assertThat(findPlayer.getTeam()).isEqualTo(team);
        assertThat(findPlayer.getTeam().getRegion()).isEqualTo(region);
    }

    @Test
    void setInfoTest() {
    	//given
        Sns sns = new Sns("sns2", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team, GENERAL);
        playerRepository.save(player);

    	//when
        player.setUniformNumber(10);
        player.setPosition(GK);

        em.flush();
        em.clear();

        Player findPlayer = playerRepository.findById(player.getId()).orElseThrow();

        //then
        assertThat(findPlayer.getUniformNumber()).isEqualTo(10);
        assertThat(findPlayer.getPosition()).isEqualTo(GK);
    }

    @Test
    void setStatusTest() {
        //given
        Sns sns = new Sns("sns3", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team, GENERAL);
        playerRepository.save(player);

    	//when
        player.setStatus(EXIT);

        em.flush();
        em.clear();

        Player findPlayer = playerRepository.findById(player.getId()).orElseThrow();

        //then
        assertThat(findPlayer.getStatus()).isEqualTo(EXIT);
    }

    @Test
    void setAuthorityTest() {
        //given
        Sns sns = new Sns("sns4", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team, GENERAL);
        playerRepository.save(player);

    	//when
        player.setAuthority(LEADER);

        em.flush();
        em.clear();

        Player findPlayer = playerRepository.findById(player.getId()).orElseThrow();

        //then
        assertThat(findPlayer.getAuthority()).isEqualTo(LEADER);
    }
}