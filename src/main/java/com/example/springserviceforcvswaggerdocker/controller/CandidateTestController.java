package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.CandidateTest;
import com.example.springserviceforcvswaggerdocker.entity.Specialization;
import com.example.springserviceforcvswaggerdocker.service.CandidateTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(CandidateTestController.class);
    private final CandidateTestService candidateTestService;

    @Autowired
    public CandidateTestController(CandidateTestService candidateTestService) {
        this.candidateTestService = candidateTestService;
    }

    @Operation(summary = "Get all Candidate Tests paged", description = "Get all Candidate Tests paged", tags = { "candidate test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CandidateTest.class))))
    })
    @GetMapping(produces = "application/json")
    public Page<CandidateTest> getCandidateTests(
            @Parameter(description = "Filter by mark") @RequestParam(value = "mark", required = false) Integer mark,
            @Parameter(description = "Sort by field") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @Parameter(description = "Parameters for pagination") @RequestParam(required = false) Pageable pageable) {

        LOGGER.info("Getting all candidate tests.");

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

    @Operation(summary = "Add a new candidate test", description = "Add a new candidate test", tags = { "candidate test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully")
    })
    @PostMapping(produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<CandidateTest> add(@Parameter(description = "Create a new candidate test", required = true) @RequestBody CandidateTest candidateTest) {
        LOGGER.info("Adding new candidate test.");
        CandidateTest updatedCandidateTest = candidateTestService.store(candidateTest);
        return new ResponseEntity<>(updatedCandidateTest, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a candidate test", description = "Update a candidate test", tags = { "candidate test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "201", description = "Created successfully")
    })
    @PutMapping(value = "/{id}", produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<CandidateTest> update(@Parameter(description = "Create a new test", required = true) @RequestBody CandidateTest candidateTest,
                                                @Parameter(description = "Test id for updating", required = true) @PathVariable Long id) {
        LOGGER.info("Updating candidate test with id: " + id);

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
