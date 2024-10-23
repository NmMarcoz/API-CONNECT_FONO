package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.UUID;

@Entity
@Table(name = "fonoevaluation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FonoEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

//    @OneToOne
//    @JoinColumn(name = "medical_record_id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private MedicalRecord medicalRecord;

    // Fala e Linguagem
    @Column(name = "sound_production")
    private String soundProduction;

    @Column(name = "verbal_comprehension")
    private String verbalComprehension;

    @Column(name = "fluency")
    private String fluency;

    @Column(name = "vocal_quality")
    private String vocalQuality;

    @Column(name = "articulation")
    private String articulation;

    //Avaliação Auditiva

    @Column(name = "audition")
    private String audition;

    @Column(name = "exam_result")
    private String examResult;

    //Avaliação Orofacial

    @Column(name = "breathing")
    private String breathing;

    @Column(name = "swallowing")
    private String swallowing;

    @Column(name = "orofacial_movements")
    private String orofacialMovements;

    //Cognitiva e Comportamental

    @Column(name = "attention")
    private String attention;

    @Column(name = "focus")
    private String focus;

    @Column(name = "memory")
    private String memory;

    @Column(name = "behavior")
    private  String behavior;
}
