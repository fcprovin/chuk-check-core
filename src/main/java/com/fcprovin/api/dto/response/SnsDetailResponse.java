package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Sns;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SnsDetailResponse extends SnsResponse {

    private final MemberResponse member;

    public static SnsDetailResponse of(Sns sns) {
        return SnsDetailResponse.builder()
                .id(sns.getId())
                .uuid(sns.getUuid())
                .type(sns.getType())
                .createdDate(sns.getCreatedDate())
                .updatedDate(sns.getUpdatedDate())
                .member(MemberResponse.of(sns.getMember()))
                .build();
    }
}
