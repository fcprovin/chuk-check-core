package com.fcprovin.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    void read() {
        //then
        assertThatThrownBy(() -> adminService.read(-1L), "Not exist admin")
                .isInstanceOf(IllegalArgumentException.class);

    }
}