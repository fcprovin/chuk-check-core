package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    
}
