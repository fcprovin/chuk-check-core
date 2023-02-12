package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void saveTest() {
        //given
        Member member = Member.builder().name("memberA").build();

        //when
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).orElseThrow();

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void saveSnsTest() {
        //given
        Sns sns1 = new Sns("sns1", SnsType.GOOGLE);
        em.persist(sns1);

        //when
        Member memberA = new Member("memberA", "testA@test.com", LocalDate.of(1997, 3, 7), sns1);
        memberRepository.save(memberA);
        Member findMember = memberRepository.findById(memberA.getId()).orElseThrow();

        //then
        assertThat(findMember).isEqualTo(memberA);
        assertThat(findMember.getId()).isEqualTo(memberA.getId());

        assertThat(findMember.getSns()).isEqualTo(sns1);
        assertThat(findMember.getSns().getUuid()).isEqualTo(sns1.getUuid());
        assertThat(findMember.getSns().getType()).isEqualTo(sns1.getType());
    }
}