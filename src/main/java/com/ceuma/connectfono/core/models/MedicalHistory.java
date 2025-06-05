package com.ceuma.connectfono.core.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "medical_record_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MedicalRecord medicalRecord;

    @Column(name = "annotations", columnDefinition = "TEXT")
    private String annotations;

    @Column(name = "complementary_exams", columnDefinition = "TEXT")
    private String complementaryExams;

    @Column(name = "orientation", columnDefinition = "TEXT")
    private String orientation;

    @OneToMany
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Questions> questions;

}