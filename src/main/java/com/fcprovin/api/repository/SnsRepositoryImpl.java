package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.SnsSearch;
import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fcprovin.api.entity.QSns.sns;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class SnsRepositoryImpl implements SnsQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Sns> findQueryBySearch(SnsSearch search) {
        return queryFactory
                .selectFrom(sns)
                .where(uuidEqual(search.getUuid()), typeEqual(search.getType()))
                .fetch();
    }

    private BooleanExpression uuidEqual(String uuid) {
        return hasText(uuid) ? sns.uuid.eq(uuid) : null;
    }

    private BooleanExpression typeEqual(SnsType type) {
        return nonNull(type) ? sns.type.eq(type) : null;
    }
}
