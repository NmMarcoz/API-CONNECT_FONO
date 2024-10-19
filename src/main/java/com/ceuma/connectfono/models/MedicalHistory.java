package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "medical_record_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MedicalRecord medicalRecord;

    @OneToMany
    @JsonManagedReference
    private List<Questions> questions;

}