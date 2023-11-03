package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.entity.Candidate;
import com.example.springserviceforcvswaggerdocker.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

@Service
public class CandidateService {
    private static final Logger LOGGER = LogManager.getLogger(CandidateService.class);


    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate store(Candidate candidate, MultipartFile photo, MultipartFile cvFile) {
        try {
            LOGGER.info("Storing candidate data for: " + candidate.getName());
            candidate.setPhoto(photo.getBytes());
            candidate.setCvFile(cvFile.getBytes());
        } catch (IOException e) {
            LOGGER.error("Error while storing candidate data for: " + candidate.getName(), e);
            throw new RuntimeException(e);
        }
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> findById(Long id) {
        LOGGER.info("Finding candidate by id: " + id);
        return candidateRepository.findById(id);
    }

    public Page<Candidate> findAll(Pageable pageable) {
        LOGGER.info("Finding all candidates");
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> findByNameContaining(String name, Pageable pageable) {
        LOGGER.info("Finding candidate by name: " + name);
        return candidateRepository.findByNameContaining(name, pageable);
    }
}
