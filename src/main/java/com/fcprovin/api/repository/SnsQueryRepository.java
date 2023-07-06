package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.SnsSearch;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;

import java.util.List;

public interface SnsQueryRepository {

    List<Sns> findQueryBySearch(SnsSearch search);
}
