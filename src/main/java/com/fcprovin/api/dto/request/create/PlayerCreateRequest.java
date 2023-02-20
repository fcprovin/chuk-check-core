package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Team;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
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
