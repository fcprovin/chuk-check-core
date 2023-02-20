package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.entity.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteQueryRepository {

    List<Vote> findQueryBySearch(VoteSearch search);
    Optional<Vote> findQueryById(Long id);
}
