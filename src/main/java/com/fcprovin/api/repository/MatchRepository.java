package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchQueryRepository {
    
}
