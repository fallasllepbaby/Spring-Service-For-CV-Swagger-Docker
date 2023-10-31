package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.repository.CandidateTestRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateTestService {
    private static final Logger LOGGER = LogManager.getLogger(CandidateTestService.class);

    private final CandidateTestRepository candidateTestRepository;

    @Autowired
    public CandidateTestService(CandidateTestRepository candidateTestRepository) {
        this.candidateTestRepository = candidateTestRepository;
    }

    public CandidateTest store(CandidateTest candidateTest) {
        LOGGER.info("Storing candidate data for candidate test with id: " + candidateTest.getId());
        return candidateTestRepository.save(candidateTest);
    }

    public Optional<CandidateTest> findById(Long id) {
        LOGGER.info("Finding candidate test by id: " + id);
        return candidateTestRepository.findById(id);
    }

    public Page<CandidateTest> findAll(Pageable pageable) {
        LOGGER.info("Finding all candidate tests");
        return candidateTestRepository.findAll(pageable);
    }

    public Page<CandidateTest> findByMark(Integer mark, Pageable pageable) {
        LOGGER.info("Finding candidate test by mark: " + mark);
        return candidateTestRepository.findByMark(mark, pageable);
    }
}
