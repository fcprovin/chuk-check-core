package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.MatchSearch;
import com.fcprovin.api.entity.Match;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QMatch.match;
import static com.fcprovin.api.entity.QStadium.stadium;
import static com.fcprovin.api.entity.QTeam.team;
import static java.time.LocalTime.MAX;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Match> findQueryBySearch(MatchSearch search) {
        return queryFactory
                .selectFrom(match)
                .join(match.team, team).fetchJoin()
                .join(match.stadium, stadium).fetchJoin()
                .where(
                        teamIdEqual(search.getTeamId()),
                        stadiumIdEqual(search.getStadiumId()),
                        matchDateBetween(search.getStartDate(), search.getEndDate())
                ).fetch();
    }

    @Override
    public Optional<Match> findQueryById(Long id) {
        return ofNullable(queryFactory
                .selectFrom(match)
                .join(match.team, team).fetchJoin()
                .join(match.stadium, stadium).fetchJoin()
                .where(match.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression teamIdEqual(Long id) {
        return nonNull(id) ? match.team.id.eq(id) : null;
    }

    private BooleanExpression stadiumIdEqual(Long id) {
        return nonNull(id) ? match.stadium.id.eq(id) : null;
    }

    private BooleanExpression matchDateBetween(LocalDate startDate, LocalDate endDate) {
        return nonNull(startDate) && nonNull(endDate) ? match.matchDate.startDate.goe(startDate.atStartOfDay())
                        .and(match.matchDate.endDate.loe(endDate.atTime(MAX))) : null;
    }
}
