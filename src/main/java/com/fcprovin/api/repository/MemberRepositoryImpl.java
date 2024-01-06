package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QMember.member;
import static com.fcprovin.api.entity.QSns.sns;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findQueryBySearch(MemberSearch search) {
        return queryFactory
                .selectFrom(member)
                .join(member.sns, sns).fetchJoin()
                .where(nameEqual(search.getName()), emailEqual(search.getEmail()), snsIdEqual(search.getSnsId()))
                .fetch();
    }

    @Override
    public Optional<Member> findQueryById(Long id) {
        return ofNullable(queryFactory
                .selectFrom(member)
                .join(member.sns, sns).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<Member> findQueryByIdAndSnsUuid(Long id, String uuid) {
        return ofNullable(queryFactory
                .selectFrom(member)
                .join(member.sns, sns).fetchJoin()
                .where(member.id.eq(id), member.sns.uuid.eq(uuid))
                .fetchOne());
    }

    private BooleanExpression nameEqual(String name) {
        return hasText(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression emailEqual(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression snsIdEqual(Long snsId) {
        return nonNull(snsId) ? sns.id.eq(snsId) : null;
    }
}
