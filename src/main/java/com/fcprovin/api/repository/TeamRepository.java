package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Stadium;
import com.fcprovin.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
}
