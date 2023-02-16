package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fcprovin.api.entity.SnsType.GOOGLE;
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
        Member member = new Member("memberA");

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
        Sns sns1 = new Sns("sns1", GOOGLE);
        em.persist(sns1);

        Member memberA = new Member("memberA", sns1);

        //when
        memberRepository.save(memberA);
        Member findMember = memberRepository.findById(memberA.getId()).orElseThrow();

        //then
        assertThat(findMember).isEqualTo(memberA);
        assertThat(findMember.getId()).isEqualTo(memberA.getId());

        assertThat(findMember.getSns()).isEqualTo(sns1);
        assertThat(findMember.getSns().getUuid()).isEqualTo(sns1.getUuid());
        assertThat(findMember.getSns().getType()).isEqualTo(sns1.getType());
    }

    @Test
    void queryTest() {
    	//given
        Sns sns1 = new Sns("sns1", GOOGLE);

        em.persist(sns1);
        em.persist(new Member("park", sns1));

        //when
        List<Member> result = memberRepository.findQueryBySearch(MemberSearch.builder()
                .name("park")
                .build());

        Member findMember = result.get(0);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(findMember.getSns().getType()).isEqualTo(sns1.getType());
        assertThat(findMember.getSns().getUuid()).isEqualTo(sns1.getUuid());
    }
}