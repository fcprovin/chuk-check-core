package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
}
