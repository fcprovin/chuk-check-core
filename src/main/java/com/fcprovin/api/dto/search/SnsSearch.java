package com.fcprovin.api.dto.search;

import com.fcprovin.api.entity.SnsType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnsSearch {

    private String uuid;
    private SnsType type;
}
