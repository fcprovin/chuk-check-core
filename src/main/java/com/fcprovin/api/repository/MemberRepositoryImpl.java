package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QMember.member;
import static com.fcprovin.api.entity.QPlayer.player;
import static com.fcprovin.api.entity.QSns.sns;
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
                .where(nameEqual(search.getName()), emailEqual(search.getEmail()))
                .fetch();
    }

    @Override
    public Optional<Member> findQueryById(Long id) {
        return ofNullable(queryFactory
                .selectFrom(member)
                .join(member.sns, sns).fetchJoin()
                .leftJoin(member.players, player).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression nameEqual(String name) {
        return hasText(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression emailEqual(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }
}
