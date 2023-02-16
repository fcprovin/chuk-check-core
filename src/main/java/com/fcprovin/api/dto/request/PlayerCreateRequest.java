package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Team;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class PlayerCreateRequest {

    @NotEmpty
    private final Long memberId;

    @NotEmpty
    private final Long teamId;

    @NotEmpty
    private final PlayerAuthority authority;

    public Player toEntity(Member member, Team team) {
        return Player.builder()
                .member(member)
                .team(team)
                .authority(authority)
                .build();

    }
}
