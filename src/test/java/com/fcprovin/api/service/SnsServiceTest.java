package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.SnsCreateRequest;
import com.fcprovin.api.entity.Sns;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.fcprovin.api.entity.SnsType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class SnsServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    SnsService snsService;

    @Test
    void validate() {
    	//given
        em.persist(new Sns("uuid", GOOGLE));

        //then
        assertThatThrownBy(() -> snsService.create(SnsCreateRequest.builder()
                .uuid("uuid")
                .type(GOOGLE)
                .build()), "Already sns")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> snsService.read(Long.MIN_VALUE), "Not exist sns")
                .isInstanceOf(IllegalArgumentException.class);
    }
}