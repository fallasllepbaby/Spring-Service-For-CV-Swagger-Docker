package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.repository.CandidateRepository;
import com.example.springserviceforcvswaggerdocker.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity add(@RequestPart("candidate") Candidate candidate, @RequestPart("photo") MultipartFile photo) throws IOException {
        return ResponseEntity.ok(candidateService.store(candidate, photo));
    }

}
