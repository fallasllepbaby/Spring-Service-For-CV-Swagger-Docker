package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import com.example.springserviceforcvswaggerdocker.service.CandidateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/candidatetests")
public class CandidateTestController {

    private final CandidateTestService candidateTestService;

    @Autowired
    public CandidateTestController(CandidateTestService candidateTestService) {
        this.candidateTestService = candidateTestService;
    }

    @GetMapping
    public Page<CandidateTest> getCandidateTests(
            @RequestParam(value = "mark", required = false) Integer mark,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            Pageable pageable) {

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, sort);
        Sort sortDirection = Sort.by(order);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortDirection);

        Page<CandidateTest> candidateTests;

        if (mark != null) {
            candidateTests = candidateTestService.findByMark(mark, pageable);
        } else {
            candidateTests = candidateTestService.findAll(pageable);
        }

        return candidateTests;
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
