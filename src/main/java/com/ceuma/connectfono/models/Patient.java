package com.ceuma.connectfono.models;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "patient")
@RequiredArgsConstructor
@EqualsAndHashCode
public class Patient extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true)
    private Long id;
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", unique = false)
    @Size(min = 6, max = 18)
    private String password;

    //@JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "ra", unique = true)
    @Size(max = 6)
    private String ra;

    @OneToMany(mappedBy = "patient")
    @JsonProperty(access = Access.WRITE_ONLY)
   // @JoinColumn(name= "patient_id", nullable = false, updatable = true)
    private List<Dependent> dependents;

    @OneToMany(mappedBy = "patient")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Consultation> consultations;

    @OneToMany
    @JsonProperty(access = Access.READ_ONLY)
    private List<Schedule> schedules;

}
