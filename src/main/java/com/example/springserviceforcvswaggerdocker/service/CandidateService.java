package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate store(Candidate candidate, MultipartFile photo, MultipartFile cvFile) {
        try {
            candidate.setPhoto(photo.getBytes());
            candidate.setCvFile(cvFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    public Page<Candidate> findAll(Pageable pageable) {
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> findByNameContaining(String name, Pageable pageable) {
        return candidateRepository.findByNameContaining(name, pageable);
    }
}
