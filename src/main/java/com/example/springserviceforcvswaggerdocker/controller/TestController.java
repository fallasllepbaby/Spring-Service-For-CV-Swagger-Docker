package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Test;
import com.example.springserviceforcvswaggerdocker.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<Test> add(@RequestBody Test test) {
        testService.store(test);
        return new ResponseEntity<>(test, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Test> update(@RequestBody Test test, @PathVariable Long id) {
        Optional<Test> currentTest = testService.findById(id);
        if (!currentTest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        testService.store(test);
        return new ResponseEntity<>(test, HttpStatus.CREATED);
    }
}
