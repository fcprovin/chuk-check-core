package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MatchSearch;
import com.fcprovin.api.entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchQueryRepository {

    List<Match> findQueryBySearch(MatchSearch search);
    Optional<Match> findQueryById(Long id);
}
