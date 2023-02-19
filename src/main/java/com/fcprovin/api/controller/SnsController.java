package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.create.SnsCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.SnsResponse;
import com.fcprovin.api.service.SnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fcprovin.api.dto.response.SnsResponse.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sns")
public class SnsController {

    private final SnsService snsService;

    @PostMapping
    public BaseResponse<SnsResponse> create(@RequestBody @Valid SnsCreateRequest request) {
        return new BaseResponse<>(of(snsService.create(request)));
    }

    @GetMapping
    public BaseResponse<List<SnsResponse>> readAll() {
        return new BaseResponse<>(snsService.readAll().stream()
                .map(SnsResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<SnsResponse> read(@PathVariable Long id) {
        return new BaseResponse<>(of(snsService.read(id)));
    }
}
