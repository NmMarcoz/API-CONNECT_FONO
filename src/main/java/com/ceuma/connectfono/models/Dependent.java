package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dependent")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Dependent extends Person{
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patient patient;

    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name ="relationship")
    private String relationship;
}
