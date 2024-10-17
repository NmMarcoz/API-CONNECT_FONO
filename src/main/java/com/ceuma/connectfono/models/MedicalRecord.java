package com.ceuma.connectfono.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "motive", columnDefinition = "TEXT")
    private String motive;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "sent_by")
    private String sentBy;

    @ManyToOne()
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;


}
