package com.ceuma.connectfono.services;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Questions;
import com.ceuma.connectfono.repositories.MedicalRecordRepository;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalHistoryService medicalHistoryService;
    @Autowired
    QuestionsService questionsService;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Transactional
    public MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO){
        MedicalRecord medicalRecord = medicalRecordDTO.getMedicalRecord();
        //MedicalHistory medicalHistory = medicalRecordDTO.getMedicalHistory();
        List<Questions> questionsList = medicalRecordDTO.getQuestions();
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);
        questionsList.forEach(questions -> {
            questions.setMedicalHistory(medicalHistorySaved);
        });


        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        List<Questions> questionsSaved = questionsService.createLot(questionsList);

        medicalHistory.setMedicalRecord( medicalRecord);
        medicalHistory.setQuestions(questionsList);







        MedicalRecordDTO medicalRecordDTOSaved = new MedicalRecordDTO(medicalRecordSaved, medicalHistorySaved, questionsSaved);

        return medicalRecordDTOSaved;
    }

    public List<MedicalRecord> getAll(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário cadastrado");
        }
        return medicalRecords;
    }

    public MedicalRecord getById(UUID id){
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
                ()-> new BadRequestException("Nenhum prontuário com esse ID"));
        return medicalRecord;
    }

    public List<MedicalRecord> getByPatientId(UUID id){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientID(id);
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário referente a esse paciente");
        }
        return medicalRecords;
    }

}
