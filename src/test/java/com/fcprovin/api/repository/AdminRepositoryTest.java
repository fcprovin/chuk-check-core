package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Admin;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    AdminRepository adminRepository;

    @Test
    void save() {
        //given
        Admin admin = new Admin("admin");

        //when
        Admin saveAdmin = adminRepository.save(admin);
        Admin findAdmin = adminRepository.findById(saveAdmin.getId()).get();

        //then
        assertThat(findAdmin).isEqualTo(admin);
        assertThat(findAdmin.getName()).isEqualTo(admin.getName());
    }

    @Test
    void findById() {
        //given
        Admin admin = new Admin("admin");
        em.persist(admin);

        //when
        Admin findAdmin = adminRepository.findById(admin.getId()).get();

        //then
        assertThat(findAdmin).isEqualTo(admin);
        assertThat(findAdmin.getName()).isEqualTo(admin.getName());
    }

}