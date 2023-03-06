package com.fcprovin.api.controller;

import com.fcprovin.api.docs.EnumDocs;
import com.fcprovin.api.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class CommonController {

    @GetMapping("/enums")
    public BaseResponse<?> enumReadAll() {
        return new BaseResponse<>(new EnumDocs());
    }
}
