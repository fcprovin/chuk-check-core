package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Sns;

import java.util.Optional;

public interface SnsQueryRepository {

    Optional<Sns> findQueryById(Long id);
}
