package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.RegionRequest;
import com.fcprovin.api.dto.request.TeamCreateRequest;
import com.fcprovin.api.dto.request.TeamUpdateRequest;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Region;
import com.fcprovin.api.entity.Team;
import com.fcprovin.api.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.BaseStatus.WAIT;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(value = false)
class TeamServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    RegionService regionService;

    Long regionId;

    @BeforeEach
    void regionCreate() {
        Region region = regionService.create(new RegionRequest("경기도", "성남시"));
        regionId = region.getId();
    }

    @BeforeEach
    void createMemberAndTeam() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");
        Member member4 = new Member("member4");
        Member member5 = new Member("member5");

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(teamA);
        em.persist(teamB);

        for (int i = 0; i < 100; i++) {
            Team selectedTeam = i % 2 == 0 ? teamA : teamB;
            Member selectedMember;

            if (i % 5 == 0) selectedMember = member1;
            else if (i % 5 == 1) selectedMember = member2;
            else if (i % 5 == 2) selectedMember = member3;
            else if (i % 5 == 3) selectedMember = member4;
            else selectedMember = member5;

            em.persist(new Player(selectedMember, selectedTeam));
        }
    }

    @Test
    void create() {
    	//given
        TeamCreateRequest teamCreateRequest = new TeamCreateRequest(regionId, "프로빈");

        //when
        Region region = regionService.read(regionId);
        Team team = teamCreateRequest.toEntity(region);
        Team saveTeam = teamRepository.save(team);

        //then
        assertThat(saveTeam.getRegion()).isEqualTo(region);
        assertThat(saveTeam.getId()).isEqualTo(team.getId());
        assertThat(saveTeam.getName()).isEqualTo(team.getName());
        assertThat(saveTeam.getRegion().getCountry()).isEqualTo(region.getCountry());
        assertThat(saveTeam.getRegion().getCity()).isEqualTo(region.getCity());
    }

    @Test
    void createTDD() {
        //given
        TeamCreateRequest teamCreateRequest = new TeamCreateRequest(regionId, "프로빈");

        //when
        Team saveTeam = teamService.create(teamCreateRequest);

        //then
        assertThat(saveTeam.getId()).isGreaterThan(0);
        assertThat(saveTeam.getStatus()).isEqualTo(WAIT);
        assertThat(saveTeam.getName()).isEqualTo(teamCreateRequest.getName());
        assertThat(saveTeam.getRegion().getId()).isEqualTo(teamCreateRequest.getRegionId());
    }

    @Test
    void changeStatusTDDTest() {
    	//given
        TeamCreateRequest teamCreateRequest = new TeamCreateRequest(regionId, "프로빈");
        Team team = teamService.create(teamCreateRequest);

        TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest();
        teamUpdateRequest.setStatus(APPROVE);

        //when
        Team updateTeam = teamService.update(team.getId(), teamUpdateRequest);
        Team findTeam = teamService.read(updateTeam.getId());

        //then
        assertThat(findTeam.getStatus()).isEqualTo(APPROVE);
        assertThat(findTeam.getName()).isEqualTo(updateTeam.getName());
    }
    
    @Test
    void readAll() {
        //given
        em.flush();
        em.clear();

        List<Team> teams = teamService.read();

        for (Team team : teams) {
            System.out.println("team.getPlayers().size() = " + team.getPlayers().size());
        }
//        team.getPlayers().forEach(p -> System.out.println("p.getMember().getName() = " + p.getMember().getName()));
    }

    @Test
    void readSearchTDD() {
    	//given
        Region region = new Region("서울", "잠실");
        em.persist(region);

        Team team = new Team("team2", region);
        em.persist(team);

        //when
        Team findTeam = teamService.readDetail(team.getId());

        //then
        assertThat(findTeam.getId()).isEqualTo(team.getId());
        assertThat(findTeam.getName()).isEqualTo(team.getName());
    }
}