package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.MemberCreateRequest;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;

    @Test
    void validateName() {
        //given
        Sns sns = new Sns("sns1", GOOGLE);
        em.persist(sns);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(2022, 1, 1) ,sns);
        em.persist(member);

        //then
        assertThatThrownBy(() -> memberService.create(MemberCreateRequest.builder()
                .snsId(sns.getId())
                .name("memberA")
                .email("test2@test.com")
                .birthDate(member.getBirthDate())
                .build()), "Already user name").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateEmail() {
        //given
        Sns sns = new Sns("sns1", GOOGLE);
        em.persist(sns);

        Member member = new Member("memberA", "test@test.com", LocalDate.of(2022, 1, 1) ,sns);
        em.persist(member);

        //then
        assertThatThrownBy(() -> memberService.create(MemberCreateRequest.builder()
                .snsId(sns.getId())
                .name("memberB")
                .email("test@test.com")
                .birthDate(member.getBirthDate())
                .build()), "Already user email").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> memberService.read(Long.MIN_VALUE), "Not exist user")
                .isInstanceOf(IllegalArgumentException.class);
    }
}