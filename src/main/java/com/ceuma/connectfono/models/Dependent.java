package com.ceuma.connectfono.models;

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
    private Patient patient;

    @Column(name ="relationship")
    private String relationship;
}
