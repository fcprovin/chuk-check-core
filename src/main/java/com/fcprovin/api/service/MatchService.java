package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.MatchCreateRequest;
import com.fcprovin.api.dto.request.update.MatchUpdateRequest;
import com.fcprovin.api.dto.search.MatchSearch;
import com.fcprovin.api.entity.*;
import com.fcprovin.api.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamService teamService;
    private final StadiumService stadiumService;

    public Match create(MatchCreateRequest request) {
        return matchRepository.save(request.toEntity(findTeam(request.getTeamId()), findStadium(request.getStadiumId())));
    }

    private Team findTeam(Long id) {
        return teamService.read(id);
    }

    private Stadium findStadium(Long id) {
        return stadiumService.read(id);
    }

    public Match update(Long id, MatchUpdateRequest request) {
        Match match = read(id);

        stadium(request, match);
        otherTeamName(request, match);
        home(request, match);
        notice(request, match);
        status(request, match);
        matchDate(request, match);
        voteDate(request, match);
        attendDeadLineDate(request, match);

        return match;
    }

    @Transactional(readOnly = true)
    public List<Match> readAll(MatchSearch search) {
        return matchRepository.findQueryBySearch(search);
    }

    @Transactional(readOnly = true)
    public Match read(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist match"));
    }

    @Transactional(readOnly = true)
    public Match readDetail(Long id) {
        return matchRepository.findQueryById(id).orElseThrow(() -> new IllegalArgumentException("Not exist match"));
    }

    private void stadium(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getStadiumId())) match.setStadium(findStadium(request.getStadiumId()));
    }

    private void otherTeamName(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getOtherTeamName())) match.setOtherTeamName(request.getOtherTeamName());
    }

    private void home(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getHome())) match.setHome(request.getHome());
    }

    private void notice(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getNotice())) match.setNotice(request.getNotice());
    }

    private void status(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getStatus())) match.setStatus(request.getStatus());
    }

    private void matchDate(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getStartDate()) && nonNull(request.getEndDate())) {
            match.setMatchDate(MatchDate.of(request.getStartDate(), request.getEndDate()));
        }
    }

    private void voteDate(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getVoteStartDate()) && nonNull(request.getVoteEndDate())) {
            match.setVoteDate(VoteDate.of(request.getVoteStartDate(), request.getVoteEndDate()));
        }
    }

    private void attendDeadLineDate(MatchUpdateRequest request, Match match) {
        if (nonNull(request.getAttendDeadlineDate())) {
            match.setAttendDate(AttendDate.of(request.getAttendDeadlineDate()));
        }
    }
}
