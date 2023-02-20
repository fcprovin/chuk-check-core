package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.SnsRequest;
import com.fcprovin.api.entity.Sns;
import com.fcprovin.api.repository.SnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SnsService {

    private final SnsRepository snsRepository;

    public Sns create(SnsRequest request) {
        validate(request);

        return snsRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<Sns> readAll() {
        return snsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sns read(Long id) {
        return snsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist sns"));
    }

    private void validate(SnsRequest request) {
        if (snsRepository.findByUuidAndType(request.getUuid(), request.getType()).isPresent()) {
            throw new IllegalArgumentException("Already sns");
        }
    }
}