package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Team;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PlayerCreateRequest {

    @NotEmpty
    private Long memberId;

    @NotEmpty
    private Long teamId;

    @NotEmpty
    private PlayerAuthority authority;

    @Builder
    public PlayerCreateRequest(Long memberId, Long teamId, PlayerAuthority authority) {
        this.memberId = memberId;
        this.teamId = teamId;
        this.authority = authority;
    }

    public Player toEntity(Member member, Team team) {
        return Player.builder()
                .member(member)
                .team(team)
                .authority(authority)
                .build();
    }
}
