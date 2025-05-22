package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordCreateDto {
    private MedicalHistory medicalHistory;
    private MedicalRecord medicalRecord;

}
