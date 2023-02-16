package com.fcprovin.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fcprovin.api.dto.response.BaseResponse.Message.SUCCESS;

@Getter
@JsonInclude(NON_NULL)
public class BaseResponse<T> {

    private final int code;
    private final String message;
    private T result;

    public BaseResponse() {
        this.code = HttpStatus.OK.value();
        this.message = SUCCESS.name();
    }

    public BaseResponse(T result) {
        this.code = HttpStatus.OK.value();
        this.message = SUCCESS.name();
        this.result = result;
    }

    public BaseResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    enum Message {
        SUCCESS, FAIL
    }
}
