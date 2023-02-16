package com.fcprovin.api.dto.request;

import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.entity.SnsType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SnsRequest {

    @NotEmpty
    private final String uuid;

    @NotEmpty
    private final SnsType type;

    public Sns toEntity() {
        return Sns.builder().uuid(uuid).type(type).build();
    }
}
