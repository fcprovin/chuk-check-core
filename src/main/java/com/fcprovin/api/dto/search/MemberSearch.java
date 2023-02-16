package com.fcprovin.api.dto.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSearch {

    private String name;
    private String email;
}
