package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.TeamCreateRequest;
import com.fcprovin.api.dto.request.TeamUpdateRequest;
import com.fcprovin.api.dto.search.TeamSearch;
import com.fcprovin.api.entity.Region;
import com.fcprovin.api.entity.Team;
import com.fcprovin.api.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final RegionService regionService;

    public Team create(TeamCreateRequest request) {
        validateName(request);

        return teamRepository.save(request.toEntity(findRegion(request)));
    }

    @Transactional(readOnly = true)
    public List<Team> read() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Team> readSearch(TeamSearch search) {
        return teamRepository.findQueryBySearch(search);
    }

    @Transactional(readOnly = true)
    public Team readDetail(Long id) {
        return teamRepository.findQueryById(id).orElseThrow(() -> new IllegalArgumentException("Not exist team"));
    }

    @Transactional(readOnly = true)
    public Team read(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist team"));
    }

    public Team update(Long id, TeamUpdateRequest request) {
        Team findTeam = read(id);

        if (nonNull(request.getStatus())) findTeam.setStatus(request.getStatus());

        return findTeam;
    }

    private Region findRegion(TeamCreateRequest request) {
        return regionService.read(request.getRegionId());
    }

    private void validateName(TeamCreateRequest request) {
        if (teamRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Already team name");
        }
    }
}
