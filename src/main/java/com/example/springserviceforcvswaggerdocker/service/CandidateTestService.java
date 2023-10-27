package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.repository.CandidateTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateTestService {

    private final CandidateTestRepository candidateTestRepository;

    @Autowired
    public CandidateTestService(CandidateTestRepository candidateTestRepository) {
        this.candidateTestRepository = candidateTestRepository;
    }

    public CandidateTest store(CandidateTest candidateTest) {
        CandidateTest updatedCandidateTest = new CandidateTest();
        updatedCandidateTest.setCandidate(candidateTest.getCandidate());
        updatedCandidateTest.setTest(candidateTest.getTest());
        updatedCandidateTest.setLocalDate(candidateTest.getLocalDate());
        updatedCandidateTest.setMark(candidateTest.getMark());
        return candidateTestRepository.save(updatedCandidateTest);
    }

    public Optional<CandidateTest> findById(Long id) {
        return candidateTestRepository.findById(id);
    }
}
