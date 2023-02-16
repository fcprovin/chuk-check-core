package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.StadiumRequest;
import com.fcprovin.api.entity.Stadium;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class StadiumServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    StadiumService stadiumService;

    @Test
    void createTDD() {
    	//given
        StadiumRequest request = new StadiumRequest("공설", "성남");

        //when
        Stadium stadium = stadiumService.create(request);

        //then
        assertThat(stadium.getId()).isGreaterThan(0);
        assertThat(stadium.getName()).isEqualTo(request.getName());
        assertThat(stadium.getAddress()).isEqualTo(request.getAddress());
    }

    @Test
    void readAllTDD() {
        //given
        Stadium stadium = new Stadium("공설", "성남");
        em.persist(stadium);

        //when
        List<Stadium> stadiums = stadiumService.readAll();

        assertThat(stadiums).containsExactly(stadium);
    }

    @Test
    void readTDD() {
    	//given
        Stadium stadium = new Stadium("공설", "성남");
        em.persist(stadium);

    	//when
        Stadium findStadium = stadiumService.read(stadium.getId());

    	//then
        assertThat(findStadium.getId()).isEqualTo(stadium.getId());
        assertThat(findStadium.getName()).isEqualTo(stadium.getName());
        assertThat(findStadium.getAddress()).isEqualTo(stadium.getAddress());
    }
}