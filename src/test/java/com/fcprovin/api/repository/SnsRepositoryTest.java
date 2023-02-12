package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SnsRepositoryTest {

    @Autowired
    SnsRepository snsRepository;

    @Test
    void saveTest() {
        //given
        Sns sns = Sns.builder().uuid("sns10").type(SnsType.APPLE).build();

        //when
        Sns saveSns = snsRepository.save(sns);
        Sns findSns = snsRepository.findById(saveSns.getId()).orElseThrow();

        //then
        assertThat(findSns.getId()).isEqualTo(sns.getId());
        assertThat(findSns.getUuid()).isEqualTo(sns.getUuid());
        assertThat(findSns.getType()).isEqualTo(sns.getType());
    }
}