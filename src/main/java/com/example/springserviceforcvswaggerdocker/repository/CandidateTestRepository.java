package com.example.springserviceforcvswaggerdocker.repository;

import com.example.springserviceforcvswaggerdocker.model.CandidateTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateTestRepository extends JpaRepository<CandidateTest, Long> {
    Page<CandidateTest> findByMark(Integer mark, Pageable pageable);
}
