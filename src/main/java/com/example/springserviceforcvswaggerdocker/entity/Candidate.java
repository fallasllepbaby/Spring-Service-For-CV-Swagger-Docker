package com.example.springserviceforcvswaggerdocker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;


import java.util.Set;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String middlename;
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] photo;

    private String description;
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] cvFile;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<CandidateTest> candidateTests;

    @ManyToMany
    @JoinTable(
            name = "candidate_specialization",
            joinColumns = @JoinColumn(name = "candidate_id"),
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCvFile() {
        return cvFile;
    }

    public void setCvFile(byte[] cvFile) {
        this.cvFile = cvFile;
    }

    public Set<CandidateTest> getCandidateTests() {
        return candidateTests;
    }

    public void setCandidateTests(Set<CandidateTest> candidateTests) {
        this.candidateTests = candidateTests;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations = specializations;
    }
}
