package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.repository.CandidateTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return candidateTestRepository.save(candidateTest);
    }

    public Optional<CandidateTest> findById(Long id) {
        return candidateTestRepository.findById(id);
    }

    public Page<CandidateTest> findAll(Pageable pageable) {
        return candidateTestRepository.findAll(pageable);
    }

    public Page<CandidateTest> findByMark(Integer mark, Pageable pageable) {
        return candidateTestRepository.findByMark(mark, pageable);
    }
}
