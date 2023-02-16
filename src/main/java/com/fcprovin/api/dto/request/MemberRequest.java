package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemberRequest {

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
                .name(name)
                .email(email)
                .birthDate(birthDate)
                .sns(sns)
                .build();
    }
}
