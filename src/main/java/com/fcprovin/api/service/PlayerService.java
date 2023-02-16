package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.PlayerCreateRequest;
import com.fcprovin.api.dto.request.PlayerUpdateRequest;
import com.fcprovin.api.dto.search.PlayerSearch;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Player;
import com.fcprovin.api.entity.Team;
import com.fcprovin.api.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final MemberService memberService;
    private final TeamService teamService;

    public Player create(PlayerCreateRequest request) {
        Member member = findMember(request);
        Team team = findTeam(request);

        return playerRepository.save(request.toEntity(member, team));
    }

    @Transactional(readOnly = true)
    public List<Player> readAll() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Player> readSearch(PlayerSearch search) {
        return playerRepository.findQueryBySearch(search);
    }

    @Transactional(readOnly = true)
    public Player read(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist Player"));
    }

    @Transactional(readOnly = true)
    public Player readDetail(Long id) {
        return playerRepository.findQueryById(id).orElseThrow(() -> new IllegalArgumentException("Not exist Player"));
    }

    public Player update(Long id, PlayerUpdateRequest request) {
        Player findPlayer = read(id);

        uniformNumber(request, findPlayer);
        position(request, findPlayer);
        status(request, findPlayer);
        authority(request, findPlayer);

        return findPlayer;
    }

    private void uniformNumber(PlayerUpdateRequest request, Player player) {
        if (nonNull(request.getUniformNumber())) {
            player.setUniformNumber(request.getUniformNumber());
        }
    }

    private void position(PlayerUpdateRequest request, Player player) {
        if (nonNull(request.getPosition())) {
            player.setPosition(request.getPosition());
        }
    }

    private void status(PlayerUpdateRequest request, Player player) {
        if (nonNull(request.getStatus())) {
            player.setStatus(request.getStatus());
        }
    }

    private void authority(PlayerUpdateRequest request, Player player) {
        if (nonNull(request.getAuthority())) {
            player.setAuthority(request.getAuthority());
        }
    }

    private Member findMember(PlayerCreateRequest request) {
        return memberService.read(request.getMemberId());
    }

    private Team findTeam(PlayerCreateRequest request) {
        return teamService.read(request.getTeamId());
    }
}
