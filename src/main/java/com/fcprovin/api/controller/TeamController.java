package com.fcprovin.api.controller;

import com.fcprovin.api.dto.request.create.TeamCreateRequest;
import com.fcprovin.api.dto.request.update.TeamUpdateRequest;
import com.fcprovin.api.dto.response.BaseResponse;
import com.fcprovin.api.dto.response.TeamResponse;
import com.fcprovin.api.dto.search.TeamSearch;
import com.fcprovin.api.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fcprovin.api.dto.response.TeamResponse.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public BaseResponse<TeamResponse> create(@RequestBody TeamCreateRequest teamCreateRequest) {
        return new BaseResponse<>(of(teamService.create(teamCreateRequest)));
    }

    @PutMapping("/{id}")
    public BaseResponse<TeamResponse> update(@PathVariable(name = "id") Long id,
                                             @RequestBody TeamUpdateRequest teamUpdateRequest) {
        return new BaseResponse<>(of(teamService.update(id, teamUpdateRequest)));
    }

    @GetMapping
    public BaseResponse<List<TeamResponse>> readAll(TeamSearch teamSearch) {
        return new BaseResponse<>(teamService.readSearch(teamSearch).stream()
                .map(TeamResponse::of)
                .collect(toList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<TeamResponse> read(@PathVariable(name = "id") Long id) {
        return new BaseResponse<>(of(teamService.readDetail(id)));
    }
}
