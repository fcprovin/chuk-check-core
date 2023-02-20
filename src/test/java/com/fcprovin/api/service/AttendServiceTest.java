package com.fcprovin.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class AttendServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    AttendService attendService;

    @Test
    void read() {
    	//then
        assertThatThrownBy(() -> attendService.read(Long.MIN_VALUE), "Not exist attend")
                .isInstanceOf(IllegalArgumentException.class);
    }
}