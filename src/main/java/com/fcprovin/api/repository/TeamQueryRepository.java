package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.TeamSearch;
import com.fcprovin.api.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamQueryRepository {

    List<Team> findQueryBySearch(TeamSearch search);
    Optional<Team> findQueryById(Long id);
}
