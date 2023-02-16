package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepository {

    List<Member> findQueryBySearch(MemberSearch search);
    Optional<Member> findQueryById(Long id);
}
