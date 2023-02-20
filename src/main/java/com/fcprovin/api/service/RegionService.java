package com.fcprovin.api.service;

import com.fcprovin.api.dto.request.create.RegionCreateRequest;
import com.fcprovin.api.entity.Region;
import com.fcprovin.api.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public Region create(RegionCreateRequest request) {
        validate(request);
        return regionRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<Region> readAll() {
        return regionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Region read(Long id) {
        return regionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist region"));
    }

    private void validate(RegionCreateRequest request) {
        if (regionRepository.findByCountryAndCity(request.getCountry(), request.getCity()).isPresent()) {
            throw new IllegalArgumentException("Already region");
        }
    }
}
