package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.StadiumCreateRequest;
import com.fcprovin.api.entity.Stadium;
import com.fcprovin.api.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public Stadium create(StadiumCreateRequest request) {
        return stadiumRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<Stadium> readAll() {
        return stadiumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Stadium read(Long id) {
        return stadiumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found stadium"));
    }
}
