package com.example.springserviceforcvswaggerdocker.service;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.model.Test;
import com.example.springserviceforcvswaggerdocker.repository.TestRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {
    private static final Logger LOGGER = LogManager.getLogger(TestService.class);


    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public Test store(Test test) {
        LOGGER.info("Storing test data for: " + test.getName());
        return testRepository.save(test);
    }

    public Optional<Test> findById(Long id) {
        LOGGER.info("Finding test by id: " + id);
        return testRepository.findById(id);
    }

    public Page<Test> findAll(Pageable pageable) {
        LOGGER.info("Finding all test");
        return testRepository.findAll(pageable);
    }

    public Page<Test> findByNameContaining(String name, Pageable pageable) {
        LOGGER.info("Finding test by name: " + name);
        return testRepository.findByNameContaining(name, pageable);
    }
}
