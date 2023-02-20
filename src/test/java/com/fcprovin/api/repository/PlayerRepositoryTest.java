package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static com.fcprovin.api.entity.PlayerAuthority.GENERAL;
import static com.fcprovin.api.entity.PlayerAuthority.LEADER;
import static com.fcprovin.api.entity.Position.GK;
import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PlayerRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void save() {
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
        Player findPlayer = playerRepository.findById(savePlayer.getId()).get();

        //then
        assertThat(findPlayer).isEqualTo(player);
        assertThat(findPlayer.getId()).isEqualTo(player.getId());
        assertThat(findPlayer.getMember()).isEqualTo(member);
        assertThat(findPlayer.getMember().getSns()).isEqualTo(sns);
        assertThat(findPlayer.getTeam()).isEqualTo(team);
        assertThat(findPlayer.getTeam().getRegion()).isEqualTo(region);
    }

    @Test
    void update() {
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

        em.persist(player);

    	//when
        player.setUniformNumber(10);
        player.setPosition(GK);
        player.setAuthority(LEADER);
        player.setStatus(APPROVE);

        Player findPlayer = playerRepository.findById(player.getId()).orElseThrow();

        //then
        assertThat(findPlayer.getUniformNumber()).isEqualTo(10);
        assertThat(findPlayer.getPosition()).isEqualTo(GK);
        assertThat(findPlayer.getAuthority()).isEqualTo(LEADER);
        assertThat(findPlayer.getStatus()).isEqualTo(APPROVE);
    }

    @Test
    void findQueryBySearch() {
    	//given
        Sns sns = new Sns("sns2", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);

        em.persist(player);

        PlayerSearch search = PlayerSearch.builder()
                .authority(GENERAL)
                .status(WAIT)
                .build();

        //when
        List<Player> result = playerRepository.findQueryBySearch(search);

        //then
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result).containsExactly(player);
    }

    @Test
    void findQueryById() {
    	//given
    	Sns sns = new Sns("sns2", GOOGLE);
        Region region = new Region("경기도", "성남시");

        em.persist(sns);
        em.persist(region);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(1997, 3, 7), sns);
        Team team = new Team("프로빈", region);

        em.persist(member);
        em.persist(team);

        Player player = new Player(member, team);

        em.persist(player);

    	//when
        Player findPlayer = playerRepository.findQueryById(player.getId()).get();

        //then
        assertThat(findPlayer.getTeam().getName()).isEqualTo(team.getName());
        assertThat(findPlayer.getMember().getName()).isEqualTo(member.getName());
    }
}