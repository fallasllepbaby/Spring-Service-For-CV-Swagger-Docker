package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Service
public class SpecializationService {
    private static final Logger LOGGER = LogManager.getLogger(SpecializationService.class);

    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization store(Specialization specialization) {
        LOGGER.info("Storing specialization data for: " + specialization.getName());
        return specializationRepository.save(specialization);
    }

    public Optional<Specialization> findById(Long id) {
        LOGGER.info("Finding specialization by id: " + id);
        return specializationRepository.findById(id);
    }

    public Page<Specialization> findAll(Pageable pageable) {
        LOGGER.info("Finding all specializations");
        return specializationRepository.findAll(pageable);
    }

    public Page<Specialization> findByNameContaining(String name, Pageable pageable) {
        LOGGER.info("Finding specialization by name: " + name);
        return specializationRepository.findByNameContaining(name, pageable);
    }
}
