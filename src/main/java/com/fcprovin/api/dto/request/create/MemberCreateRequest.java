package com.fcprovin.api.dto.request.create;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class MemberCreateRequest {

    @NotEmpty
    private final Long snsId;

    @NotEmpty
    private final String name;

    @Email
    private final String email;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private final LocalDate birthDate;

    public Member toEntity(Sns sns) {
        return Member.builder()
                .sns(sns)
                .name(name)
                .email(email)
                .birthDate(birthDate)
                .build();
    }
}
