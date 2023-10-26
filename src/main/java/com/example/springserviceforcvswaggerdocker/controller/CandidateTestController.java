package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.service.CandidateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/candidatetests")
public class CandidateTestController {

    private final CandidateTestService candidateTestService;

    @Autowired
    public CandidateTestController(CandidateTestService candidateTestService) {
        this.candidateTestService = candidateTestService;
    }

    @PostMapping
    public ResponseEntity<CandidateTest> add(@RequestBody CandidateTest candidateTest) {
        candidateTestService.store(candidateTest);
        return new ResponseEntity<>(candidateTest, HttpStatus.CREATED);
    }

}
