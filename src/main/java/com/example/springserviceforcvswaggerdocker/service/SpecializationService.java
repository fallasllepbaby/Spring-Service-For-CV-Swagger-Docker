package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization store(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    public Optional<Specialization> findById(Long id) {
        return specializationRepository.findById(id);
    }

    public Page<Specialization> findAll(Pageable pageable) {
        return specializationRepository.findAll(pageable);
    }

    public Page<Specialization> findByNameContaining(String name, Pageable pageable) {
        return specializationRepository.findByNameContaining(name, pageable);
    }
}
