package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate store(Candidate candidate, MultipartFile photo) throws IOException {
        candidate.setPhoto(photo.getBytes());
        //candidate.setCvFile(cvFile.getBytes());
        return candidateRepository.save(candidate);
    }
}
