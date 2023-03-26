package com.fcprovin.api.controller;

import com.fcprovin.api.exception.AccessDeniedException;
import com.fcprovin.api.exception.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/authentication")
    public void authentication() {
        throw new AuthenticationException("Authentication failed");
    }

    @GetMapping("/access-denied")
    public void accessDenied() {
        throw new AccessDeniedException("Access denied");
    }
}
