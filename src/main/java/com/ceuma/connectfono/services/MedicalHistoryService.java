package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.repositories.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryService {
    @Autowired
    MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistory create(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    public MedicalHistory findByMedicalRecordId(Long id){
        MedicalHistory medicalHistory = medicalHistoryRepository.findByMedicalRecordId(id);
        if(medicalHistory == null){
            throw new BadRequestException("Sem anamnese cadastrada para esse prontuário");
        }
        return medicalHistory;
    }


}
