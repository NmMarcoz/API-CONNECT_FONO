package com.ceuma.connectfono.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

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
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "consult_name")
    private String consultName;

    @Column(name = "motive", columnDefinition = "TEXT")
    private String motive;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "sent_by")
    private String sentBy;

    @ManyToOne()
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    //Avaliação Fonoaudiologica
    @OneToOne
    @JoinColumn(name = "fono_evaluation_id")
    private FonoEvaluation fonoEvaluation;

    @OneToOne
    @JoinColumn(name =  "medical_history_id")
    private MedicalHistory medicalHistory;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    //Clinical History
    @Column(name = "familiar_history", columnDefinition = "TEXT")
    private String familiarHistory;

    @Column(name = "health_history", columnDefinition = "TEXT")
    private String healthStory;

    @Column(name = "developmental_history", columnDefinition = "TEXT")
    private String developmentalHistory;

   //Plano Terapeutico

    @Column(name = "objectives")
    private String objectives;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "terapy_recommendations")
    private String terapyRecommendations;

    // Evolução do Tratamento
    @Column(name = "evolution_date")
    private LocalDate evolutionDate;

    @Column(name = "observed_evolution", columnDefinition = "TEXT")
    private String observedEvolution;

    @Column(name = "interventions", columnDefinition = "TEXT")
    private String interventions;

    @Column(name = "orientations", columnDefinition = "TEXT")
    private String orientations;

    // Encerramento do Tratamento

    @Column(name = "patientCondition")
    private String patientCondition;

    @Column(name = "recommendations")
    private String recommendations;
}
