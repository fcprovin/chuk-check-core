package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Stadium;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class StadiumRepositoryTest {

    @Autowired
    StadiumRepository stadiumRepository;

    @Test
    void saveTest() {
    	//given
        Stadium stadium = new Stadium("탄천 A", "성남시 탄천", 31.4, 127.5);

        //when
        Stadium saveStadium = stadiumRepository.save(stadium);
        Stadium findStadium = stadiumRepository.findById(saveStadium.getId()).orElseThrow();

        //then
        assertThat(findStadium).isEqualTo(stadium);
        assertThat(findStadium.getId()).isEqualTo(stadium.getId());
        assertThat(findStadium.getName()).isEqualTo(stadium.getName());
        assertThat(findStadium.getAddress()).isEqualTo(stadium.getAddress());
        assertThat(findStadium.getLatitude()).isEqualTo(stadium.getLatitude());
        assertThat(findStadium.getLongitude()).isEqualTo(stadium.getLongitude());
    }
}