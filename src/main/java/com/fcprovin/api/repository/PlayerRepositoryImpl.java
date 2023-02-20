package com.fcprovin.api.repository;

import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.api.entity.QMember.member;
import static com.fcprovin.api.entity.QPlayer.player;
import static com.fcprovin.api.entity.QTeam.team;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Player> findQueryBySearch(PlayerSearch search) {
        return queryFactory
                .selectFrom(player)
                .join(player.member, member).fetchJoin()
                .join(player.team, team).fetchJoin()
                .where(
                        memberIdEqual(search.getMemberId()),
                        teamIdEqual(search.getTeamId()),
                        positionEqual(search.getPosition()),
                        statusEqual(search.getStatus()),
                        authorityEqual(search.getAuthority())
                ).fetch();
    }

    @Override
    public Optional<Player> findQueryById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(player)
                .join(player.member, member).fetchJoin()
                .join(player.team, team).fetchJoin()
                .where(player.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression memberIdEqual(Long id) {
        return nonNull(id) ? player.member.id.eq(id) : null;
    }

    private BooleanExpression teamIdEqual(Long id) {
        return nonNull(id) ? player.team.id.eq(id) : null;
    }

    private BooleanExpression positionEqual(Position position) {
        return nonNull(position) ? player.position.eq(position) : null;
    }

    private BooleanExpression statusEqual(BaseStatus status) {
        return nonNull(status) ? player.status.eq(status) : null;
    }

    private Predicate authorityEqual(PlayerAuthority authority) {
        return nonNull(authority) ? player.authority.eq(authority) : null;
    }
}
