package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
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
