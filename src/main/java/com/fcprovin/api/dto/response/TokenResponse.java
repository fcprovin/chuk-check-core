package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime accessTokenExpireDate;
    private final LocalDateTime refreshTokenExpireDate;

    public TokenResponse(String accessToken,
                         String refreshToken,
                         LocalDateTime accessTokenExpireDate,
                         LocalDateTime refreshTokenExpireDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
    }
}
