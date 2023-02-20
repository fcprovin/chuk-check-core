package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.TeamSearch;
import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Team;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QRegion.region;
import static com.fcprovin.api.entity.QTeam.team;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Team> findQueryBySearch(TeamSearch search) {
        return queryFactory
                .selectFrom(team)
                .join(team.region, region).fetchJoin()
                .where(
                        regionIdEqual(search.getRegionId()),
                        nameEq(search.getName()),
                        statusEqual(search.getStatus())
                ).fetch();
    }

    @Override
    public Optional<Team> findQueryById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(team)
                .join(team.region, region).fetchJoin()
                .where(team.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression regionIdEqual(Long id) {
        return nonNull(id) ? team.region.id.eq(id) : null;
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? team.name.eq(name) : null;
    }

    private BooleanExpression statusEqual(BaseStatus status) {
        return nonNull(status) ? team.status.eq(status) : null;
    }
}
