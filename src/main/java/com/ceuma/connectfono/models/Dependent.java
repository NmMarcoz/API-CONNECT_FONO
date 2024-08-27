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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

}
