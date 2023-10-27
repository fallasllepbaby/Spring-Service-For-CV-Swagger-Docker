package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Candidate> add(@RequestPart("candidate") Candidate candidate,
                                         @RequestPart("photo") MultipartFile photo,
                                         @RequestPart("cvFile") MultipartFile cvFile) throws IOException {
        Candidate candidateWithFiles = candidateService.store(candidate, photo, cvFile);
        return new ResponseEntity<>(candidateWithFiles, HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Candidate> update(@RequestPart("candidate") Candidate candidate,
                                            @RequestPart("photo") MultipartFile photo,
                                            @RequestPart("cvFile") MultipartFile cvFile,
                                            @PathVariable Long id) throws IOException {
        Optional<Candidate> currentCandidate = candidateService.findById(id);
        if(!currentCandidate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Candidate candidateWithFiles = candidateService.store(candidate, photo, cvFile);
        return new ResponseEntity<>(candidateWithFiles, HttpStatus.NO_CONTENT);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> update(@RequestPart("candidate") Candidate candidate,
                                 @RequestPart("photo") MultipartFile photo,
                                 @RequestPart("cvFile") MultipartFile cvFile,
                                 @PathVariable Long id) {

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
