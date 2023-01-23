package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Sns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnsRepository extends JpaRepository<Sns, Long> {
    
}
