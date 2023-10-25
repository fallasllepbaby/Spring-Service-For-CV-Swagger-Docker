package com.example.springserviceforcvswaggerdocker.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "test")
    private Set<CandidateTest> candidateTestSet;

    @ManyToMany
    @JoinTable(
            name = "test_specialization",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Set<Specialization> specializations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CandidateTest> getCandidateTestSet() {
        return candidateTestSet;
    }

    public void setCandidateTestSet(Set<CandidateTest> candidateTestSet) {
        this.candidateTestSet = candidateTestSet;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations = specializations;
    }
}
