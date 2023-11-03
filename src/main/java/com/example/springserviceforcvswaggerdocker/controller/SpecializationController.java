package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.entity.Specialization;
import com.example.springserviceforcvswaggerdocker.service.SpecializationService;
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

    @GetMapping
    public Page<Specialization> getAll(@RequestParam(value = "name", required = false) String name,
                                       Pageable pageable) {

        LOGGER.info("Getting all specializations.");
        Page<Specialization> specializations;

        if (name != null) {
            specializations = specializationService.findByNameContaining(name, pageable);
        } else {
            specializations = specializationService.findAll(pageable);
        }

        return specializations;
    }

    @PostMapping
    public ResponseEntity<Specialization> add(@RequestBody Specialization specialization) {
        LOGGER.info("Adding new specialization: " + specialization.getName());
        specializationService.store(specialization);
        return new ResponseEntity<>(specialization, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> update(@RequestBody Specialization specialization,
                                            @PathVariable Long id) {
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
