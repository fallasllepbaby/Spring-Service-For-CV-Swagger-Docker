package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@RestController
@RequestMapping("/v1/candidates")
public class CandidateController {
    private static final Logger LOGGER = LogManager.getLogger(CandidateController.class);

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public Page<Candidate> getAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            Pageable pageable) {
        LOGGER.info("Getting all candidates.");

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, sort);
        Sort sortDirection = Sort.by(order);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortDirection);

        Page<Candidate> candidates;

        if (name != null) {
            candidates = candidateService.findByNameContaining(name, pageable);
        } else {
            candidates = candidateService.findAll(pageable);
        }

        return candidates;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Candidate> add(@RequestPart("candidate") Candidate candidate,
                                         @RequestPart("photo") MultipartFile photo,
                                         @RequestPart("cvFile") MultipartFile cvFile) throws IOException {
        LOGGER.info("Adding a new candidate: " + candidate.getName());
        Candidate candidateWithFiles = candidateService.store(candidate, photo, cvFile);
        return new ResponseEntity<>(candidateWithFiles, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> update(@RequestPart("candidate") Candidate candidate,
                                            @RequestPart("photo") MultipartFile photo,
                                            @RequestPart("cvFile") MultipartFile cvFile,
                                            @PathVariable Long id) {
        LOGGER.info("Updating candidate with id: " + id);

        return candidateService.findById(id).map(updatedCandidate -> {
            updatedCandidate.setName(candidate.getName());
            updatedCandidate.setSurname(candidate.getSurname());
            updatedCandidate.setMiddlename(candidate.getMiddlename());
            updatedCandidate.setDescription(candidate.getDescription());
            candidateService.store(updatedCandidate, photo, cvFile);
            return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
        }).orElseGet(() -> {
            Candidate candidateWithFiles = candidateService.store(candidate, photo, cvFile);
            return new ResponseEntity<>(candidateWithFiles, HttpStatus.CREATED);
        });
    }

}
