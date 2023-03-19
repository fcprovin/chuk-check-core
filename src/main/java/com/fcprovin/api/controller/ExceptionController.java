package com.fcprovin.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/authentication")
    public void authentication() {
        throw new IllegalArgumentException();
    }

    @GetMapping("/access/denied")
    public void accessDenied() {
        throw new IllegalArgumentException();
    }
}
