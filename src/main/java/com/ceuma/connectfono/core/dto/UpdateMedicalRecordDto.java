package com.ceuma.connectfono.core.dto;

import com.ceuma.connectfono.core.models.FonoEvaluation;
import com.ceuma.connectfono.core.models.MedicalHistory;
import com.ceuma.connectfono.core.models.MedicalRecord;
import com.ceuma.connectfono.core.models.Questions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UpdateMedicalRecordDto {
    private Optional<MedicalRecord> medicalRecord;
    private Optional<MedicalHistory> medicalHistory;
    private Optional<FonoEvaluation> fonoEvaluation;
    private Optional<List<Questions>> questions;
}
