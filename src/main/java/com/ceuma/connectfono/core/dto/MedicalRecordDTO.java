package com.ceuma.connectfono.core.dto;

import com.ceuma.connectfono.core.models.FonoEvaluation;
import com.ceuma.connectfono.core.models.MedicalHistory;
import com.ceuma.connectfono.core.models.MedicalRecord;
import com.ceuma.connectfono.core.models.Questions;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "medical_record")
    private MedicalRecord medicalRecord;

    @JsonProperty(value = "fono_evaluation", access = JsonProperty.Access.WRITE_ONLY)
    private FonoEvaluation fonoEvaluation;

    @JsonProperty(value = "medical_history")
    private MedicalHistory medicalHistory;
    //@JsonProperty(value = "questions", access = JsonProperty.Access.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Questions> questions;
}
