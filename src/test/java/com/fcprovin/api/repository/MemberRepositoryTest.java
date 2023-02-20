package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Sns sns1 = new Sns("sns1", GOOGLE);
        em.persist(sns1);

        Member memberA = new Member("memberA", sns1);

        //when
        memberRepository.save(memberA);
        Member findMember = memberRepository.findById(memberA.getId()).get();

        //then
        assertThat(findMember).isEqualTo(memberA);
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getName()).isEqualTo(memberA.getName());
    }

    @Test
    void findByName() {
        //given
        Sns sns1 = new Sns("sns1", GOOGLE);
        em.persist(sns1);

        Member memberA = new Member("memberA", sns1);

        //when
        memberRepository.save(memberA);
        Member findMember = memberRepository.findByName("memberA").get();

        //then
        assertThat(findMember).isEqualTo(memberA);
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getName()).isEqualTo(memberA.getName());
    }

    @Test
    void findByEmail() {
        //given
        Sns sns1 = new Sns("sns1", GOOGLE);
        em.persist(sns1);

        Member memberA = new Member("memberA", "test@test.com", LocalDate.of(2022, 1, 1) ,sns1);

        //when
        memberRepository.save(memberA);
        Member findMember = memberRepository.findByEmail("test@test.com").get();

        //then
        assertThat(findMember).isEqualTo(memberA);
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getName()).isEqualTo(memberA.getName());
    }

    @Test
    void findQueryBySearch() {
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

    @Test
    void findQueryById() {
    	//given
        Sns sns1 = new Sns("sns1", GOOGLE);
        em.persist(sns1);

        Member member = new Member("park", sns1);
        em.persist(member);

        //when
        Member findMember = memberRepository.findQueryById(member.getId()).get();

        //then
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getSns().getType()).isEqualTo(sns1.getType());
        assertThat(findMember.getSns().getUuid()).isEqualTo(sns1.getUuid());
    }
}