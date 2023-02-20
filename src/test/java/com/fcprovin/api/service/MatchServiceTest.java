package com.fcprovin.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MatchServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MatchService matchService;

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> matchService.read(Long.MIN_VALUE), "Not exist match")
                .isInstanceOf(IllegalArgumentException.class);
    }
}