package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRepository extends JpaRepository<Attend, Long> {
    
}
