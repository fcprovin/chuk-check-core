package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.RegionCreateRequest;
import com.fcprovin.api.entity.Region;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class RegionServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    RegionService regionService;

    @Test
    void validate() {
    	//given
        em.persist(new Region("경기도", "성남시"));

    	//then
        assertThatThrownBy(() -> regionService.create(RegionCreateRequest.builder()
                .country("경기도")
                .city("성남시")
                .build()), "Already region").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> regionService.read(Long.MIN_VALUE), "Not exist region")
                .isInstanceOf(IllegalArgumentException.class);
    }
}