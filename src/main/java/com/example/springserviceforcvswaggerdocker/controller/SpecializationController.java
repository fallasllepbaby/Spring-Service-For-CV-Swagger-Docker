package com.example.springserviceforcvswaggerdocker.controller;

import com.example.springserviceforcvswaggerdocker.model.Candidate;
import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }
    @PostMapping
    public ResponseEntity<Specialization> add(@RequestBody Specialization specialization) {
        specializationService.store(specialization);
        return new ResponseEntity<>(specialization, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> update(@RequestBody Specialization specialization,
                                            @PathVariable Long id) {

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
