package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.create.StadiumCreateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.StadiumResponse;
import com.fcprovin.api.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fcprovin.api.dto.response.StadiumResponse.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stadium")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping
    public BaseResponse<StadiumResponse> create(@RequestBody StadiumCreateRequest request) {
        return new BaseResponse<>(of(stadiumService.create(request)));
    }

    @GetMapping
    public BaseResponse<List<StadiumResponse>> readAll() {
        return new BaseResponse<>(stadiumService.readAll().stream()
                .map(StadiumResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<StadiumResponse> read(@PathVariable Long id) {
        return new BaseResponse<>(of(stadiumService.read(id)));
    }
}
