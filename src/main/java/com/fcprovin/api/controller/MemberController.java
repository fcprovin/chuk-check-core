package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.MemberRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.MemberDetailResponse;
import com.fcprovin.api.dto.response.MemberResponse;
import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fcprovin.api.dto.response.MemberResponse.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponse<MemberResponse> create(@RequestBody MemberRequest request) {
        return new BaseResponse<>(of(memberService.create(request)));
    }

    @GetMapping
    public BaseResponse<List<MemberResponse>> readAll(MemberSearch search) {
        return new BaseResponse<>(memberService.readSearch(search).stream()
                .map(MemberResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<MemberDetailResponse> read(@PathVariable Long id) {
        return new BaseResponse<>(MemberDetailResponse.of(memberService.readDetail(id)));
    }
}
