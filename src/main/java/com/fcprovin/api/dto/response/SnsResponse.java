package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SnsResponse {

    private final Long id;
    private final String uuid;
    private final SnsType type;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    private final MemberResponse member;

    public static SnsResponse of(Sns sns) {
        return SnsResponse.builder()
                .id(sns.getId())
                .uuid(sns.getUuid())
                .type(sns.getType())
                .createdDate(sns.getCreatedDate())
                .updatedDate(sns.getUpdatedDate())
                .member(MemberResponse.of(sns.getMember()))
                .build();
    }
}
