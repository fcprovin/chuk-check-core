package com.fcprovin.api.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class StadiumServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    StadiumService stadiumService;

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> stadiumService.read(Long.MIN_VALUE), "Not exist stadium")
                .isInstanceOf(IllegalArgumentException.class);
    }
}