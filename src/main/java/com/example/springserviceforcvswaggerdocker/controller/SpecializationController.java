package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.Specialization;
import com.example.springserviceforcvswaggerdocker.entity.Test;
import com.example.springserviceforcvswaggerdocker.service.SpecializationService;
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
@RequestMapping("/v1/specializations")
public class SpecializationController {
    private static final Logger LOGGER = LogManager.getLogger(SpecializationController.class);

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Operation(summary = "Get all Specializations paged", description = "Get all Specializations paged", tags = { "specialization" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Specialization.class))))
    })
    @GetMapping(produces = "application/json")
    public Page<Specialization> getAll(@Parameter(description = "Filter by name") @RequestParam(value = "name", required = false) String name,
                                       @Parameter(description = "Parameters for pagination") @RequestParam(required = false) Pageable pageable) {

        LOGGER.info("Getting all specializations.");
        Page<Specialization> specializations;

        if (name != null) {
            specializations = specializationService.findByNameContaining(name, pageable);
        } else {
            specializations = specializationService.findAll(pageable);
        }

        return specializations;
    }

    @Operation(summary = "Add a new specialization", description = "Add a new specialization", tags = { "specialization" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PostMapping(produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<Specialization> add(@Parameter(description = "Create a new specialization", required = true) @RequestBody Specialization specialization) {
        LOGGER.info("Adding new specialization: " + specialization.getName());
        specializationService.store(specialization);
        return new ResponseEntity<>(specialization, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a specialization", description = "Update a specialization", tags = { "specialization" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "409", description = "Repeatable input")
    })
    @PutMapping(value = "/{id}", produces = { "application/json" }, consumes = {"application/json" })
    public ResponseEntity<Specialization> update(@Parameter(description = "Create a new specialization", required = true) @RequestBody Specialization specialization,
                                                 @Parameter(description = "Specialization id for updating", required = true) @PathVariable Long id) {
        LOGGER.info("Updating specialization with id: " + id);

        return specializationService.findById(id).map(updatedSpecialization -> {
            updatedSpecialization.setName(specialization.getName());
            updatedSpecialization.setDescription(specialization.getDescription());
            specializationService.store(updatedSpecialization);
            return new ResponseEntity<>(updatedSpecialization, HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>(specialization, HttpStatus.CREATED);
        });
    }
}
