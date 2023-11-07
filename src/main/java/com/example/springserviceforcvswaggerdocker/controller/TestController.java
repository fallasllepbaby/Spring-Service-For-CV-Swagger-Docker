package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.Test;
import com.example.springserviceforcvswaggerdocker.service.TestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tests")
public class TestController {

    private static final Logger LOGGER = LogManager.getLogger(TestController.class);
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public Page<Test> getAll(@RequestParam(value = "name", required = false) String name,
                                       Pageable pageable) {
        LOGGER.info("Getting all tests.");

        Page<Test> tests;

        if (name != null) {
            tests = testService.findByNameContaining(name, pageable);
        } else {
            tests = testService.findAll(pageable);
        }

        return tests;
    }

    @PostMapping
    public ResponseEntity<Test> add(@RequestBody Test test) {
        LOGGER.info("Adding new test: " + test.getName());
        testService.store(test);
        return new ResponseEntity<>(test, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Test> update(@RequestBody Test test,
                                                 @PathVariable Long id) {
        LOGGER.info("Updating test with id: " + id);

        return testService.findById(id).map(updatedTest -> {
            updatedTest.setName(test.getName());
            updatedTest.setDescription(test.getDescription());
            testService.store(updatedTest);
            return new ResponseEntity<>(updatedTest, HttpStatus.OK);
        }).orElseGet(() -> {
            Test newTest = testService.store(test);
            return new ResponseEntity<>(newTest, HttpStatus.CREATED);
        });
    }
}
