package com.fcprovin.api.dto.request.update;

import com.fcprovin.api.entity.VoteStatus;
import lombok.Data;

@Data
public class VoteUpdateRequest {

    private VoteStatus status;
}
