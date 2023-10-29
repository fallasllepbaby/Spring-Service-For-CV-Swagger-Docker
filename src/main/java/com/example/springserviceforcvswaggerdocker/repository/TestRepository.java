package com.example.springserviceforcvswaggerdocker.repository;

import com.example.springserviceforcvswaggerdocker.model.Specialization;
import com.example.springserviceforcvswaggerdocker.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findByNameContaining(String name, Pageable pageable);
}
