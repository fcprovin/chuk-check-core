package com.fcprovin.api.repository;

import com.fcprovin.api.entity.Sns;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.fcprovin.api.entity.QMember.member;
import static com.fcprovin.api.entity.QSns.sns;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class SnsRepositoryImpl implements SnsQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Sns> findQueryById(Long id) {
        return ofNullable(queryFactory
                .selectFrom(sns)
                .join(sns.member, member).fetchJoin()
                .where(sns.id.eq(id))
                .fetchOne());
    }
}
