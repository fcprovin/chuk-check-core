package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.PlayerCreateRequest;
import com.fcprovin.api.dto.request.update.PlayerUpdateRequest;
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
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final MemberService memberService;
    private final TeamService teamService;

    public Player create(PlayerCreateRequest request) {
        validate(request);

        return playerRepository.save(request.toEntity(findMember(request), findTeam(request)));
    }

    public Player update(Long id, PlayerUpdateRequest request) {
        Player findPlayer = read(id);

        uniformNumber(request, findPlayer);
        position(request, findPlayer);
        status(request, findPlayer);
        authority(request, findPlayer);

        return findPlayer;
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
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist player"));
    }

    @Transactional(readOnly = true)
    public Player readDetail(Long id) {
        return playerRepository.findQueryById(id).orElseThrow(() -> new IllegalArgumentException("Not exist player"));
    }

    @Transactional(readOnly = true)
    public List<Team> readTeamByMemberId(Long id) {
        return readSearch(PlayerSearch.ofMemberId(id)).stream()
                .map(Player::getTeam)
                .collect(toList());
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

    private void validate(PlayerCreateRequest request) {
        if (playerRepository.findByMemberIdAndTeamId(request.getMemberId(), request.getTeamId()).isPresent()) {
            throw new IllegalArgumentException("Already player");
        }
    }
}
