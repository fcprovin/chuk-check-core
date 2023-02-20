package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.AttendSearch;
import com.fcprovin.api.entity.Attend;
import com.fcprovin.api.entity.AttendStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QAttend.attend;
import static com.fcprovin.api.entity.QMatch.match;
import static com.fcprovin.api.entity.QPlayer.player;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class AttendRepositoryImpl implements AttendQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Attend> findQueryBySearch(AttendSearch search) {
        return queryFactory
                .selectFrom(attend)
                .join(attend.player, player).fetchJoin()
                .join(attend.match, match).fetchJoin()
                .where(
                        playerIdEqual(search.getPlayerId()),
                        matchIdEqual(search.getMatchId()),
                        statusEqual(search.getStatus())
                ).fetch();
    }

    @Override
    public Optional<Attend> findQueryById(Long id) {
        return ofNullable(queryFactory
                .selectFrom(attend)
                .join(attend.player, player).fetchJoin()
                .join(attend.match, match).fetchJoin()
                .where(attend.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression playerIdEqual(Long playerId) {
        return nonNull(playerId) ? attend.player.id.eq(playerId) : null;
    }

    private BooleanExpression matchIdEqual(Long matchId) {
        return nonNull(matchId) ? attend.match.id.eq(matchId) : null;
    }

    private BooleanExpression statusEqual(AttendStatus status) {
        return nonNull(status) ? attend.status.eq(status) : null;
    }
}
