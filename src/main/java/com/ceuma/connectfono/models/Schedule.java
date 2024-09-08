package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "schedule")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Patient patient;

    @NotNull
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Consultation consultation;

    @NotNull
    private Date date;
    @NotNull
    private Time hour;

}
