package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
