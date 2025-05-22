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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private String date;

    @Column(name = "consult_name")
    private String consultName;

    @Column(name = "motive")
    private String motive;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "sent_by")
    private String sentBy;




    //Avaliação Fonoaudiologica


    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "observations")
    private String observations;

    //Clinical History
    @Column(name = "familiar_history")
    private String familiarHistory;

    @Column(name = "health_history")
    private String healthStory;

    @Column(name = "developmental_history")
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
    private String evolutionDate;

    @Column(name = "observed_evolution")
    private String observedEvolution;

    @Column(name = "interventions")
    private String interventions;

    @Column(name = "orientations")
    private String orientations;

    // Encerramento do Tratamento

    @Column(name = "patientCondition")
    private String patientCondition;

    @Column(name = "recommendations")
    private String recommendations;

    @Column(name = "sign_in")
    private String signIn;
}
