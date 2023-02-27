package com.fcprovin.api;

import com.fcprovin.api.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static com.fcprovin.api.entity.PlayerAuthority.LEADER;
import static com.fcprovin.api.entity.Position.FW;
import static com.fcprovin.api.entity.Position.MF;
import static com.fcprovin.api.entity.SnsType.*;

@Profile(value = { "local" })
@Component
@RequiredArgsConstructor
public class InitRepository {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Sns sns1 = new Sns("0ee295c4-801e-4d86-8ccf-5a8419600e89", GOOGLE);
            Sns sns2 = new Sns("d493b83c-224e-4940-8a24-060d800bc526", APPLE);

            em.persist(sns1);
            em.persist(sns2);

            Member member1 = new Member("member1", "member1@google.com", LocalDate.of(2000, 12, 25), sns1);
            Member member2 = new Member("member2", "member2@apple.com", LocalDate.of(1997, 3, 7), sns2);

            em.persist(member1);
            em.persist(member2);

            Region region = new Region("경기도", "성남시");

            em.persist(region);

            Team teamA = new Team("teamA", region);
            Team teamB = new Team("teamB", region);

            em.persist(teamA);
            em.persist(teamB);

            Player player1 = new Player(member1, teamA, LEADER);
            Player player2 = new Player(member2, teamB);

            em.persist(player1);
            em.persist(player2);

            player1.setPosition(FW);
            player1.setUniformNumber(9);

            player2.setPosition(MF);
            player2.setUniformNumber(6);
        }
    }
}
