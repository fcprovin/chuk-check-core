package com.fcprovin.api.exception;

import com.fcprovin.api.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseResponse<?> authentication(AuthenticationException e) {
        log.error("Authentication - AuthenticationException", e);
        return new BaseResponse<>(UNAUTHORIZED, e.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse<?> accessDenied(AccessDeniedException e) {
        log.error("AccessDenied - AccessDeniedException", e);
        return new BaseResponse<>(FORBIDDEN, e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<?> badRequest(IllegalArgumentException e) {
        log.error("BadRequest - IllegalArgumentException : {}", e.getMessage());
        return new BaseResponse<>(BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> serverError(RuntimeException e) {
        log.error("ServerError - RuntimeException : {}", e.getMessage());
        return new BaseResponse<>(INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
