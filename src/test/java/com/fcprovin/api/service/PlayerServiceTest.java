package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.PlayerCreateRequest;
import com.fcprovin.api.dto.request.PlayerUpdateRequest;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Team;
import com.fcprovin.api.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fcprovin.api.entity.BaseStatus.APPROVE;
import static com.fcprovin.api.entity.PlayerAuthority.LEADER;
import static com.fcprovin.api.entity.PlayerAuthority.MANAGER;
import static com.fcprovin.api.entity.Position.FW;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PlayerServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerRepository playerRepository;

    Long memberId;
    Long teamId;

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

        memberId = member1.getId();
        teamId = teamA.getId();

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
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(memberId, teamId, LEADER);

        Member member = em.find(Member.class, memberId);
        Team team = em.find(Team.class, teamId);

        //when
        Player player = playerCreateRequest.toEntity(member, team);
        Player savePlayer = playerRepository.save(player);

        //then
        assertThat(savePlayer).isEqualTo(player);
        assertThat(savePlayer.getId()).isEqualTo(player.getId());
        assertThat(savePlayer.getMember()).isEqualTo(member);
        assertThat(savePlayer.getTeam()).isEqualTo(team);
    }

    @Test
    void createTDD() {
    	//given
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(memberId, teamId, LEADER);

    	//when
        Player player = playerService.create(playerCreateRequest);

    	//then
        assertThat(player.getId()).isGreaterThan(0);
        assertThat(player.getTeam().getId()).isEqualTo(teamId);
        assertThat(player.getMember().getId()).isEqualTo(memberId);
    }

    @Test
    void updateTDD() {
    	//given
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(memberId, teamId, LEADER);
        Player player = playerService.create(playerCreateRequest);

        PlayerUpdateRequest playerUpdateRequest = new PlayerUpdateRequest();
        playerUpdateRequest.setUniformNumber(9);
        playerUpdateRequest.setPosition(FW);
        playerUpdateRequest.setStatus(APPROVE);
        playerUpdateRequest.setAuthority(MANAGER);

        //when
        Player changePlayer = playerService.update(player.getId(), playerUpdateRequest);
        Player findPlayer = playerService.read(changePlayer.getId());

        //then
        assertThat(findPlayer.getUniformNumber()).isEqualTo(playerUpdateRequest.getUniformNumber());
        assertThat(findPlayer.getPosition()).isEqualTo(playerUpdateRequest.getPosition());
        assertThat(findPlayer.getStatus()).isEqualTo(playerUpdateRequest.getStatus());
        assertThat(findPlayer.getAuthority()).isEqualTo(playerUpdateRequest.getAuthority());
    }

    @Test
    void notUpdateAuthority() {
    	//given
        PlayerCreateRequest playerCreateRequest = new PlayerCreateRequest(memberId, teamId, LEADER);
        Player player = playerService.create(playerCreateRequest);

        PlayerUpdateRequest playerUpdateRequest = new PlayerUpdateRequest();
        playerUpdateRequest.setUniformNumber(9);
        playerUpdateRequest.setPosition(FW);
        playerUpdateRequest.setStatus(APPROVE);

        //when
        Player changePlayer = playerService.update(player.getId(), playerUpdateRequest);
        Player findPlayer = playerService.read(changePlayer.getId());

        //then
        assertThat(findPlayer.getUniformNumber()).isEqualTo(playerUpdateRequest.getUniformNumber());
        assertThat(findPlayer.getPosition()).isEqualTo(playerUpdateRequest.getPosition());
        assertThat(findPlayer.getStatus()).isEqualTo(playerUpdateRequest.getStatus());
        assertThat(findPlayer.getAuthority()).isEqualTo(LEADER);
    }

    @Test
    void readAll() {
        //when
        List<Player> players = playerService.readAll();

        for (Player player : players) {
            System.out.println("player = " + player.getMember().getName() + " / " + player.getTeam().getName());
        }
    }
}