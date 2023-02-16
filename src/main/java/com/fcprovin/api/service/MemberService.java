package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.MemberRequest;
import com.fcprovin.api.dto.search.MemberSearch;
import com.fcprovin.api.entity.Member;
import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SnsService snsService;

    public Member create(MemberRequest request) {
        validateName(request);
        validateEmail(request);

        return memberRepository.save(request.toEntity(findSns(request)));
    }

    @Transactional(readOnly = true)
    public List<Member> readAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Member> readSearch(MemberSearch search) {
        return memberRepository.findQueryBySearch(search);
    }

    @Transactional(readOnly = true)
    public Member read(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist user"));
    }

    @Transactional(readOnly = true)
    public Member readDetail(Long id) {
        return memberRepository.findQueryById(id).orElseThrow(() -> new IllegalArgumentException("Not exist user"));
    }

    private Sns findSns(MemberRequest memberRequest) {
        return snsService.read(memberRequest.getSnsId());
    }

    private void validateName(MemberRequest request) {
        if (memberRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Already user name");
        }
    }

    private void validateEmail(MemberRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Already user email");
        }
    }
}
