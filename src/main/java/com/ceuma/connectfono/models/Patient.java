package com.ceuma.connectfono.models;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "patient")
@RequiredArgsConstructor
@EqualsAndHashCode
public class Patient extends Person {
    @Column(name = "cpf", unique = true)
    @NotNull
    @Size(max = 14)
    private String cpf;

    //@JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "ra", unique = true)
    @Size(max = 6)
    private String ra;

    @Column(name = "birth_year")
    @NotNull
    private String birth_year;

    @Column(name = "gender")
    @NotNull
    @Size(max = 1)
    private String gender;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "education_level")
    private String education_level;

    //    @OneToMany(mappedBy = "patient")
    @OneToMany
    @JsonProperty(access = Access.WRITE_ONLY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = true)
    private List<Dependent> dependents;

//    @OneToMany(mappedBy = "patient")


    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "address")
    @NotNull
    private String address;


}
