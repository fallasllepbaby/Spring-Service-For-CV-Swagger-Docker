package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.Candidate;
import com.example.springserviceforcvswaggerdocker.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all Candidates paged", description = "Get all Candidates paged", tags = { "candidate" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Candidate.class))))
    })
    @GetMapping(produces = "application/json")
    public Page<Candidate> getAll(
            @Parameter(description = "Filter by name") @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "Sort by field") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @Parameter(description = "Parameters for pagination") @RequestParam(required = false) Pageable pageable) {
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

    @Operation(summary = "Add a new candidate", description = "Add a new candidate", tags = { "candidate" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PostMapping(produces = { "application/json" }, consumes = {"multipart/form-data" })
    public ResponseEntity<Candidate> add(@Parameter(description = "Create a new candidate", required = true) @RequestPart("candidate") Candidate candidate,
                                         @Parameter(description = "Add a photo to candidate", content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary")), required = true) @RequestPart("photo") MultipartFile photo,
                                         @Parameter(description = "Add cv file to candidate", content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary")), required = true) @RequestPart("cvFile") MultipartFile cvFile) throws IOException {
        LOGGER.info("Adding a new candidate: " + candidate.getName());
        Candidate candidateWithFiles = candidateService.store(candidate, photo, cvFile);
        return new ResponseEntity<>(candidateWithFiles, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a candidate", description = "Update a candidate", tags = { "candidate" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PutMapping(value = "/{id}", produces = { "application/json" }, consumes = {"multipart/form-data" })
    public ResponseEntity<Candidate> update(@Parameter(description = "Update a candidate info") @RequestPart("candidate") Candidate candidate,
                                            @Parameter(description = "Update a candidate photo",content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))) @RequestPart("photo") MultipartFile photo,
                                            @Parameter(description = "Update a candidate cv file",content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))) @RequestPart("cvFile") MultipartFile cvFile,
                                            @Parameter(description = "Candidate id for updating", required = true) @PathVariable Long id) {
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
