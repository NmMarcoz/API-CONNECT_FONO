package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Questions;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalRecordDTO {
    @JsonProperty("medical_record")
    private MedicalRecord medicalRecord;
    @JsonProperty("medical_history")
    private MedicalHistory medicalHistory;
    @JsonProperty("questions")
    private List<Questions> questions;
}
