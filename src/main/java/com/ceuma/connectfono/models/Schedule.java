package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private Patient patient;

    @NotNull
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Consultation consultation;

    @NotNull
    private Date date;
    @NotNull
    private String hour;

}
