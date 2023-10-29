package com.example.springserviceforcvswaggerdocker.repository;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Page<Specialization> findByNameContaining(String name, Pageable pageable);
}
