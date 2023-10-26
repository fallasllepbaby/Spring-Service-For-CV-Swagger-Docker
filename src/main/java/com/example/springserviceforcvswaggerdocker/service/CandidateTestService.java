package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.repository.CandidateTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateTestService {

    private final CandidateTestRepository candidateTestRepository;

    @Autowired
    public CandidateTestService(CandidateTestRepository candidateTestRepository) {
        this.candidateTestRepository = candidateTestRepository;
    }

    public CandidateTest store(CandidateTest candidateTest) {
        return candidateTestRepository.save(candidateTest);
    }
}
