package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class SnsResponse {

    private final Long id;
    private final String uuid;
    private final SnsType type;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static SnsResponse of(Sns sns) {
        return SnsResponse.builder()
                .id(sns.getId())
                .uuid(sns.getUuid())
                .type(sns.getType())
                .createdDate(sns.getCreatedDate())
                .updatedDate(sns.getUpdatedDate())
                .build();
    }
}
