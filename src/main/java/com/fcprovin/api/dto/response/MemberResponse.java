package com.fcprovin.api.dto.response;

import com.fcprovin.api.entity.Member;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class MemberResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate birthDate;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birthDate(member.getBirthDate())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .build();
    }
}
