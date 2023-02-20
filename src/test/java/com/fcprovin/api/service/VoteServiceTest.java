package com.fcprovin.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class VoteServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    VoteService voteService;

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> voteService.read(Long.MIN_VALUE), "Not exist vote")
                .isInstanceOf(IllegalArgumentException.class);
    }
}