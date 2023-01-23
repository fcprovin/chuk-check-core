package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    
}
