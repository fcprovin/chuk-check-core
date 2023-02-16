package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.MemberRequest;
import com.fcprovin.api.dto.request.SnsRequest;
import com.fcprovin.api.entity.*;
import com.fcprovin.api.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    EntityManager em;
    
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    SnsService snsService;

    Long snsId;

    @BeforeEach
    void snsCreate() {
        Sns sns = snsService.create(new SnsRequest("uuid", SnsType.GOOGLE));
        snsId = sns.getId();
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
        MemberRequest memberRequest = new MemberRequest(snsId, "provin", "test@test.com", LocalDate.of(1997, 3, 7));
        Sns sns = snsService.read(snsId);

        //when
        Member member = memberRequest.toEntity(sns);
        Member saveMember = memberRepository.save(member);

        //then
        assertThat(saveMember.getSns()).isEqualTo(sns);
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(saveMember.getName()).isEqualTo(memberRequest.getName());
        assertThat(saveMember.getEmail()).isEqualTo(memberRequest.getEmail());
        assertThat(saveMember.getBirthDate()).isEqualTo(memberRequest.getBirthDate());
    }

    @Test
    void createTDD() {
        //given
        MemberRequest memberRequest = new MemberRequest(snsId, "provin", "test@test.com", LocalDate.of(1997, 3, 7));

        //when
        Member member = memberService.create(memberRequest);

        //then
        assertThat(member.getId()).isGreaterThan(0);
        assertThat(member.getName()).isEqualTo(memberRequest.getName());
        assertThat(member.getEmail()).isEqualTo(memberRequest.getEmail());
        assertThat(member.getBirthDate()).isEqualTo(memberRequest.getBirthDate());
        assertThat(member.getSns().getId()).isEqualTo(snsId);
    }

    @Test
    void readTDD() {
        //given
        MemberRequest memberRequest = new MemberRequest(snsId, "provin", "test@test.com", LocalDate.of(1997, 3, 7));
        Member member = memberService.create(memberRequest);

        //when
        Member findMember = memberService.read(member.getId());

        //then
        assertThat(findMember).isEqualTo(member);
    }
    
    @Test
    void readN_plus_1() {
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findAll();
//        List<Member> result = memberRepository.findQueryAll();

        for (Member member : result) {
            System.out.println("member.getSns() = " + member.getSns());
            System.out.println("member.getPlayers().size() = " + member.getPlayers().size());
        }
    }
}