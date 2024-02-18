package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.TeamSearch;
import com.fcprovin.api.entity.Region;
import com.fcprovin.api.entity.Team;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class TeamRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void save() {
        //given
        Region region = new Region("경기도", "성남시");
        em.persist(region);

        Team team = new Team("프로빈", region);

        //when
        Team saveTeam = teamRepository.save(team);
        Team findTeam = teamRepository.findById(saveTeam.getId()).get();

        //then
        assertThat(findTeam).isEqualTo(team);
        assertThat(findTeam.getId()).isEqualTo(team.getId());
        assertThat(findTeam.getRegion().getCity()).isEqualTo(region.getCity());
        assertThat(findTeam.getRegion().getCountry()).isEqualTo(region.getCountry());
    }

    @Test
    void setStatus() {
        //given
        Region region = new Region("경기도", "성남시");
        em.persist(region);

        Team team = new Team("프로빈", region);
        em.persist(team);

        //when
        team.setStatus(APPROVE);
        Team findTeam = teamRepository.findById(team.getId()).orElseThrow();

        //then
        assertThat(findTeam.getStatus()).isEqualTo(APPROVE);
    }

    @Test
    void findByName() {
    	//given
        Region region = new Region("경기도", "성남시");
        em.persist(region);

        Team team = new Team("프로빈", region);
        em.persist(team);

    	//when
        Team findTeam = teamRepository.findByName("프로빈").get();

        //then
        assertThat(findTeam.getId()).isEqualTo(team.getId());
    }

    @Test
    void findQueryBySearch() {
    	//given
        Region region = new Region("경기도", "성남시");
        em.persist(region);

        Team team = new Team("프로빈", region);
        em.persist(team);

        TeamSearch teamSearch = TeamSearch.builder()
                .regionId(region.getId())
                .build();

        //when
        List<Team> result = teamRepository.findQueryBySearch(teamSearch);

        //then
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result).containsExactly(team);
    }

    @Test
    void findQueryById() {
    	//given
        Region region = new Region("경기도", "성남시");
        em.persist(region);

        Team team = new Team("프로빈", region);
        em.persist(team);

    	//when
        Team findTeam = teamRepository.findQueryById(team.getId()).get();

        //then
        assertThat(findTeam.getRegion()).isEqualTo(region);
        assertThat(findTeam.getRegion().getCountry()).isEqualTo("경기도");
        assertThat(findTeam.getRegion().getCity()).isEqualTo("성남시");
    }
}