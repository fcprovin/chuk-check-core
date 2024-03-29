package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByCountryAndCity(String country, String city);
}
