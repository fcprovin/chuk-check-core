package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.MatchCreateRequest;
import com.fcprovin.api.dto.request.MatchUpdateRequest;
import com.fcprovin.api.entity.Match;
import com.fcprovin.api.entity.Stadium;
import com.fcprovin.api.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.fcprovin.api.entity.MatchStatus.VOTE;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MatchServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MatchService matchService;

    @Test
    void createTDD() {
    	//given
        Team team = new Team("team1");
        Stadium stadium = new Stadium("황송", "성남");

        em.persist(team);
        em.persist(stadium);

        MatchCreateRequest request = MatchCreateRequest.builder()
                .teamId(team.getId())
                .stadiumId(stadium.getId())
                .otherTeamName("상대")
                .isHome(true)
                .notice(null)
                .startDate(of(2023, 3, 1, 10, 0))
                .endDate(of(2023, 3, 1, 12, 0))
                .voteStartDate(of(2023, 2, 20, 9, 0))
                .voteEndDate(of(2023, 2, 28, 9, 0))
                .attendDeadlineDate(of(2023, 3, 1, 9, 30))
                .build();

        //when
        Match match = matchService.create(request);

    	//then
        assertThat(match.getId()).isGreaterThan(0);
        assertThat(match.getTeam().getId()).isEqualTo(team.getId());
        assertThat(match.getStadium().getId()).isEqualTo(stadium.getId());
    }

    @Test
    void updateTDD() {
    	//given
        Team team = new Team("team1");
        Stadium stadium = new Stadium("황송", "성남");

        em.persist(team);
        em.persist(stadium);

        MatchCreateRequest request = MatchCreateRequest.builder()
                .teamId(team.getId())
                .stadiumId(stadium.getId())
                .otherTeamName("상대")
                .isHome(true)
                .notice(null)
                .startDate(of(2023, 3, 1, 10, 0))
                .endDate(of(2023, 3, 1, 12, 0))
                .voteStartDate(of(2023, 2, 20, 9, 0))
                .voteEndDate(of(2023, 2, 28, 9, 0))
                .attendDeadlineDate(of(2023, 3, 1, 9, 30))
                .build();

        Match match = matchService.create(request);

        MatchUpdateRequest updateRequest = new MatchUpdateRequest();
        updateRequest.setStatus(VOTE);
        updateRequest.setNotice("공지수정");
        updateRequest.setIsHome(false);

        //when
        Match updateMatch = matchService.update(match.getId(), updateRequest);
        Match findMatch = matchService.read(updateMatch.getId());

        //then
        assertThat(findMatch).isEqualTo(updateMatch);
        assertThat(findMatch.getStatus()).isEqualTo(updateRequest.getStatus());
        assertThat(findMatch.getNotice()).isEqualTo(updateRequest.getNotice());
        assertThat(findMatch.isHome()).isEqualTo(updateRequest.getIsHome());
    }

    @Test
    void readDetailTDD() {
    	//given
        Team team = new Team("team1");
        Stadium stadium = new Stadium("황송", "성남");

        em.persist(team);
        em.persist(stadium);

        MatchCreateRequest request = MatchCreateRequest.builder()
                .teamId(team.getId())
                .stadiumId(stadium.getId())
                .otherTeamName("상대")
                .isHome(true)
                .notice(null)
                .startDate(of(2023, 3, 1, 10, 0))
                .endDate(of(2023, 3, 1, 12, 0))
                .voteStartDate(of(2023, 2, 20, 9, 0))
                .voteEndDate(of(2023, 2, 28, 9, 0))
                .attendDeadlineDate(of(2023, 3, 1, 9, 30))
                .build();

        Match match = matchService.create(request);

    	//when
        Match findMatch = matchService.readDetail(match.getId());

    	//then
        assertThat(findMatch.getTeam().getId()).isEqualTo(team.getId());
        assertThat(findMatch.getStadium().getId()).isEqualTo(stadium.getId());
        assertThat(findMatch.getAttends().size()).isEqualTo(0);
        assertThat(findMatch.getVotes().size()).isEqualTo(0);
    }

    @Test
    void readSearchTDD() {
    	//given

    	//when

    	//then
    }
}