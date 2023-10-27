package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.model.Test;
import com.example.springserviceforcvswaggerdocker.service.CandidateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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
        CandidateTest updatedCandidateTest = candidateTestService.store(candidateTest);
        return new ResponseEntity<>(updatedCandidateTest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateTest> update(@RequestBody CandidateTest candidateTest,
                                            @PathVariable Long id) {

        return candidateTestService.findById(id).map(updatedCandidateTest -> {
            updatedCandidateTest.setCandidate(candidateTest.getCandidate());
            updatedCandidateTest.setTest(candidateTest.getTest());
            updatedCandidateTest.setLocalDate(candidateTest.getLocalDate());
            updatedCandidateTest.setMark(candidateTest.getMark());
            candidateTestService.store(updatedCandidateTest);
            return new ResponseEntity<>(updatedCandidateTest, HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>(candidateTest, HttpStatus.CREATED);
        });
    }

}
