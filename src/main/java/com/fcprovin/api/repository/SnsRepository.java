package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsRepository extends JpaRepository<Sns, Long> {

    Optional<Sns> findByUuidAndType(String uuid, SnsType type);
}
