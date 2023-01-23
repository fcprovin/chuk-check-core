package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    
}
