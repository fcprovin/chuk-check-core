package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.create.MatchCreateRequest;
import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.MatchResponse;
import com.fcprovin.api.dto.search.MatchSearch;
import com.fcprovin.api.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fcprovin.api.dto.response.MatchResponse.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/match")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public BaseResponse<MatchResponse> create(@RequestBody MatchCreateRequest request) {
        return new BaseResponse<>(of(matchService.create(request)));
    }

    @PutMapping("/{id}")
    public BaseResponse<MatchResponse> update(@PathVariable(name = "id") Long id,
                                              @RequestBody MatchUpdateRequest request) {
        return new BaseResponse<>(of(matchService.update(id, request)));
    }

    @GetMapping
    public BaseResponse<List<MatchResponse>> readAll(MatchSearch search) {
        return new BaseResponse<>(matchService.readAll(search).stream()
                .map(MatchResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<MatchResponse> read(@PathVariable(name = "id") Long id) {
        return new BaseResponse<>(of(matchService.readDetail(id)));
    }
}
