package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.fcprovin.api.entity.SnsType.APPLE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SnsRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    SnsRepository snsRepository;

    @Test
    void save() {
        //given
        Sns sns = new Sns("sns10", APPLE);

        //when
        Sns saveSns = snsRepository.save(sns);
        Sns findSns = snsRepository.findById(saveSns.getId()).orElseThrow();

        //then
        assertThat(findSns.getId()).isEqualTo(sns.getId());
        assertThat(findSns.getUuid()).isEqualTo(sns.getUuid());
        assertThat(findSns.getType()).isEqualTo(sns.getType());
    }

    @Test
    void query() {
        //given
        Sns sns = new Sns("sns10", APPLE);
        em.persist(sns);

        Member member = new Member("memberA", sns);
        em.persist(member);

        //when
        Sns findSns = snsRepository.findQueryById(sns.getId()).orElse(null);

        //then
        assertThat(findSns).isNotNull();
        assertThat(findSns.getId()).isEqualTo(sns.getId());
        assertThat(findSns.getUuid()).isEqualTo(sns.getUuid());
        assertThat(findSns.getType()).isEqualTo(sns.getType());

        assertThat(findSns.getMember().getId()).isEqualTo(member.getId());
        assertThat(findSns.getMember().getName()).isEqualTo(member.getName());
    }
}