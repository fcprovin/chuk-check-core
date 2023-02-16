package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.RegionRequest;
import com.fcprovin.api.entity.Region;
import com.fcprovin.api.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class RegionServiceTest {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    RegionService regionService;

    @Test
    void create() {
        //given
        RegionRequest regionRequest = new RegionRequest("경기도", "성남시");

        //when
        Region region = regionRequest.toEntity();
        Region saveRegion = regionRepository.save(region);

        //then
        assertThat(saveRegion.getId()).isEqualTo(region.getId());
        assertThat(saveRegion.getCountry()).isEqualTo(regionRequest.getCountry());
        assertThat(saveRegion.getCity()).isEqualTo(regionRequest.getCity());
    }

    @Test
    void createTDD() {
    	//given
        RegionRequest regionRequest = new RegionRequest("경기도", "성남시");

    	//when
    	Region region = regionService.create(regionRequest);

    	//then
        assertThat(region.getId()).isGreaterThan(0);
        assertThat(region.getCountry()).isEqualTo(regionRequest.getCountry());
        assertThat(region.getCity()).isEqualTo(regionRequest.getCity());
    }

    @Test
    void createFail() {
        //given
        RegionRequest regionRequest = new RegionRequest("경기도", "성남시");

    	//when
    	regionService.create(regionRequest);

        //then
        assertThatThrownBy(() -> regionService.create(regionRequest));
    }
}