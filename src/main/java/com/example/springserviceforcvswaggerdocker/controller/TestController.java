package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.Candidate;
import com.example.springserviceforcvswaggerdocker.entity.Test;
import com.example.springserviceforcvswaggerdocker.service.TestService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tests")
public class TestController {

    private static final Logger LOGGER = LogManager.getLogger(TestController.class);
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Operation(summary = "Get all Tests paged", description = "Get all Tests paged", tags = { "test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Test.class))))
    })
    @GetMapping(produces = "application/json")
    public Page<Test> getAll(@Parameter(description = "Filter by name") @RequestParam(value = "name", required = false) String name,
                             @Parameter(description = "Parameters for pagination") @RequestParam(required = false) Pageable pageable) {
        LOGGER.info("Getting all tests.");

        Page<Test> tests;

        if (name != null) {
            tests = testService.findByNameContaining(name, pageable);
        } else {
            tests = testService.findAll(pageable);
        }

        return tests;
    }

    @Operation(summary = "Add a new test", description = "Add a new test", tags = { "test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PostMapping(produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<Test> add(@Parameter(description = "Create a new test", required = true) @RequestBody Test test) {
        LOGGER.info("Adding new test: " + test.getName());
        testService.store(test);
        return new ResponseEntity<>(test, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a test", description = "Update a test", tags = { "test" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PutMapping(value = "/{id}", produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<Test> update(@Parameter(description = "Create a new test", required = true) @RequestBody Test test,
                                       @Parameter(description = "Test id for updating", required = true) @PathVariable Long id) {
        LOGGER.info("Updating test with id: " + id);

        return testService.findById(id).map(updatedTest -> {
            updatedTest.setName(test.getName());
            updatedTest.setDescription(test.getDescription());
            testService.store(updatedTest);
            return new ResponseEntity<>(updatedTest, HttpStatus.OK);
        }).orElseGet(() -> {
            Test newTest = testService.store(test);
            return new ResponseEntity<>(newTest, HttpStatus.CREATED);
        });
    }
}
