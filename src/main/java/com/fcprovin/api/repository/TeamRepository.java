package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamQueryRepository {

    Optional<Team> findByName(String name);
}
