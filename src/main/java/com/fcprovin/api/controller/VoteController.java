package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.create.VoteCreateRequest;
import com.fcprovin.api.dto.request.update.VoteUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.VoteResponse;
import com.fcprovin.api.dto.search.VoteSearch;
import com.fcprovin.api.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fcprovin.api.dto.response.VoteResponse.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public BaseResponse<VoteResponse> create(@RequestBody VoteCreateRequest request) {
        return new BaseResponse<>(of(voteService.create(request)));
    }

    @PutMapping("/{id}")
    public BaseResponse<VoteResponse> update(@PathVariable(name = "id") Long id,
                                             @RequestBody VoteUpdateRequest request) {
        return new BaseResponse<>(of(voteService.update(id, request)));
    }

    @GetMapping
    public BaseResponse<List<VoteResponse>> readAll(VoteSearch search) {
        return new BaseResponse<>(voteService.readAll(search).stream()
                .map(VoteResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<VoteResponse> read(@PathVariable(name = "id") Long id) {
        return new BaseResponse<>(of(voteService.read(id)));
    }
}
