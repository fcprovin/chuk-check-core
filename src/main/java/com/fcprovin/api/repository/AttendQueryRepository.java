package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.AttendSearch;
import com.fcprovin.api.entity.Attend;

import java.util.List;
import java.util.Optional;

public interface AttendQueryRepository {

    List<Attend> findQueryBySearch(AttendSearch search);
    Optional<Attend> findQueryById(Long id);
}
