package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerQueryRepository {

    List<Player> findQueryBySearch(PlayerSearch search);
    Optional<Player> findQueryById(Long id);
}
