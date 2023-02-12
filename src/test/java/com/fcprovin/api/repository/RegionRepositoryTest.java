package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class RegionRepositoryTest {

    @Autowired
    RegionRepository regionRepository;

    @Test
    void saveTest() {
        //given
        Region region = new Region("경기도", "성남시");

        //when
        Region saveRegion = regionRepository.save(region);
        Region findRegion = regionRepository.findById(saveRegion.getId()).orElseThrow();

        //then
        assertThat(findRegion).isEqualTo(region);
        assertThat(findRegion.getId()).isEqualTo(region.getId());
        assertThat(findRegion.getCountry()).isEqualTo(region.getCountry());
        assertThat(findRegion.getCity()).isEqualTo(region.getCity());
    }
}