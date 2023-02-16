package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.SnsRequest;
import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SnsServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    SnsService snsService;

    @Test
    void createTDD() {
        //given
        SnsRequest snsRequest = new SnsRequest("uuid", SnsType.GOOGLE);

        //when
        Sns sns = snsService.create(snsRequest);

        //then
        assertThat(sns.getId()).isGreaterThan(0);
        assertThat(sns.getUuid()).isEqualTo(snsRequest.getUuid());
        assertThat(sns.getType()).isEqualTo(snsRequest.getType());
    }

    @Test
    void readAllTDD() {
    	//given
        Sns sns = new Sns("uuid", SnsType.GOOGLE);
        em.persist(sns);

        //when
        List<Sns> list = snsService.readAll();

        //then
        assertThat(list).containsExactly(sns);
    }

    @Test
    void readTDD() {
    	//given
        Sns sns = new Sns("uuid", SnsType.GOOGLE);
        em.persist(sns);

        //when
        Sns findSns = snsService.read(sns.getId());

        //then
        assertThat(findSns.getId()).isEqualTo(sns.getId());
        assertThat(sns.getUuid()).isEqualTo(sns.getUuid());
        assertThat(sns.getType()).isEqualTo(sns.getType());
    }
}