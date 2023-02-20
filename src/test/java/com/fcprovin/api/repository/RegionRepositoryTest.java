package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class RegionRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    RegionRepository regionRepository;

    @Test
    void save() {
        //given
        Region region = new Region("서울시", "강남구");

        //when
        Region saveRegion = regionRepository.save(region);
        Region findRegion = regionRepository.findById(saveRegion.getId()).orElseThrow();

        //then
        assertThat(findRegion).isEqualTo(region);
        assertThat(findRegion.getId()).isEqualTo(region.getId());
        assertThat(findRegion.getCountry()).isEqualTo(region.getCountry());
        assertThat(findRegion.getCity()).isEqualTo(region.getCity());
    }

    @Test
    void findByCountryAndCity() {
        //given
        Region region = new Region("서울시", "강남구");

        em.persist(region);

        //when
        Region findRegion = regionRepository.findByCountryAndCity("서울시", "강남구").get();

        //then
        assertThat(findRegion.getId()).isEqualTo(region.getId());
    }
}