package com.example.springserviceforcvswaggerdocker.repository;

import com.example.springserviceforcvswaggerdocker.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findByNameContaining(String name, Pageable pageable);

    boolean existsByName(String name);

    boolean existsBySurname(String surname);
}
