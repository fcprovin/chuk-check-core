package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.BaseStatus;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.PlayerAuthority;
import com.fcprovin.api.entity.Position;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class PlayerResponse {

    private final Long id;
    private final int uniformNumber;
    private final Position position;
    private final BaseStatus status;
    private final PlayerAuthority authority;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static PlayerResponse of(Player player) {
        return PlayerResponse.builder()
                .id(player.getId())
                .uniformNumber(player.getUniformNumber())
                .position(player.getPosition())
                .status(player.getStatus())
                .authority(player.getAuthority())
                .createdDate(player.getCreatedDate())
                .updatedDate(player.getUpdatedDate())
                .build();
    }
}
