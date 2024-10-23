package com.ceuma.connectfono.services;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.FonoEvaluation;
import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Questions;
import com.ceuma.connectfono.repositories.FonoEvaluationRepository;
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
    @Autowired
    private FonoEvaluationRepository fonoEvaluationRepository;

    @Transactional
    public MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO){

        MedicalRecord medicalRecord = medicalRecordDTO.getMedicalRecord();
        FonoEvaluation fonoEvaluation = medicalRecordDTO.getFonoEvaluation();

        //MedicalHistory medicalHistory = medicalRecordDTO.getMedicalHistory();
        List<Questions> questionsList = medicalRecordDTO.getQuestions();
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);
        questionsList.forEach(questions -> {
            questions.setMedicalHistory(medicalHistorySaved);
        });

        medicalRecord.setFonoEvaluation(fonoEvaluation);
        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        FonoEvaluation fonoEvaluationSaved = fonoEvaluationRepository.save(fonoEvaluation);
        List<Questions> questionsSaved = questionsService.createLot(questionsList);

        medicalHistory.setMedicalRecord( medicalRecord);
        medicalHistory.setQuestions(questionsList);
        MedicalRecordDTO medicalRecordDTOSaved = new MedicalRecordDTO(medicalRecordSaved,fonoEvaluationSaved, medicalHistorySaved, questionsSaved);

        return medicalRecordDTOSaved;
    }

    public List<MedicalRecord> getAll(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário cadastrado");
        }

        return medicalRecords;
    }

    public MedicalRecordDTO getById(UUID id){
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
                ()-> new BadRequestException("Nenhum prontuário com esse ID"));
        MedicalHistory medicalHistory = medicalHistoryService.findByMedicalRecordId(medicalRecord.getId());
        FonoEvaluation fonoEvaluation = medicalRecord.getFonoEvaluation();
        List<Questions> questionsList = medicalHistory.getQuestions();
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(medicalRecord, fonoEvaluation, medicalHistory, questionsList);
        return medicalRecordDTO;
    }

    public List<MedicalRecord> getByPatientId(UUID id){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientID(id);
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário referente a esse paciente");
        }
        return medicalRecords;
    }

}
