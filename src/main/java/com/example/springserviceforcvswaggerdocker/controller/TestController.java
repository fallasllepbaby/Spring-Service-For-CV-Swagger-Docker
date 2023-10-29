package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.model.Test;
import com.example.springserviceforcvswaggerdocker.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public Page<Test> getAll(@RequestParam(value = "name", required = false) String name,
                                       Pageable pageable) {

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
        testService.store(test);
        return new ResponseEntity<>(test, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Test> update(@RequestBody Test test,
                                                 @PathVariable Long id) {

        return testService.findById(id).map(updatedTest -> {
            updatedTest.setName(test.getName());
            updatedTest.setDescription(test.getDescription());
            testService.store(updatedTest);
            return new ResponseEntity<>(updatedTest, HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>(test, HttpStatus.CREATED);
        });
    }
}
