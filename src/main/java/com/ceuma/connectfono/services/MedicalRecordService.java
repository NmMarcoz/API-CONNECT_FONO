package com.ceuma.connectfono.services;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.dto.SmallMedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.FonoEvaluation;
import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Questions;
import com.ceuma.connectfono.repositories.FonoEvaluationRepository;
import com.ceuma.connectfono.repositories.MedicalHistoryRepository;
import com.ceuma.connectfono.repositories.MedicalRecordRepository;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalHistoryService medicalHistoryService;
    @Autowired
    MedicalHistoryRepository medicalHistoryRepository;
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

        MedicalHistory medicalHistory = medicalRecordDTO.getMedicalHistory();
        List<Questions> questionsList = medicalRecordDTO.getQuestions();

        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);

        medicalHistory.setFonoEvaluation(fonoEvaluation);

        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        FonoEvaluation fonoEvaluationSaved = fonoEvaluationRepository.save(fonoEvaluation);
        List<Questions> questionsSaved = questionsService.createLot(questionsList);

        medicalHistory.setMedicalRecord( medicalRecord);
        medicalHistory.setQuestions(questionsList);
        MedicalRecordDTO medicalRecordDTOSaved = new MedicalRecordDTO(medicalRecordSaved,fonoEvaluationSaved, medicalHistorySaved, questionsSaved);

        return medicalRecordDTOSaved;
    }

    public MedicalRecord createv2(MedicalRecord medicalRecord, MedicalHistory medicalHistory){
        List<Questions> questions = medicalHistory.getQuestions();
        FonoEvaluation fonoEvaluation = medicalHistory.getFonoEvaluation();

        medicalHistory.setQuestions(null);
        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);
        questionsService.createLot(questions);

        FonoEvaluation fonoEvaluationSaved = fonoEvaluationRepository.save(fonoEvaluation);
        medicalHistorySaved.setQuestions(questions);
        medicalHistory.setFonoEvaluation(fonoEvaluationSaved);

        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);

        return medicalRecord;

    }

    public List<MedicalRecord> getAll(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAll();
       if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário cadastrado");
        }

        return medicalRecords;
    }

    public MedicalRecord getById(Integer id){
       MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
               ()-> new BadRequestException("Nenhum prontuario com esse id"));
       return medicalRecord;
    }

    public List<MedicalRecord> getByPatientId(Integer id){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientID(id);
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuário referente a esse paciente");
        }
        return medicalRecords;
    }
//    public List<SmallMedicalRecordDTO> getByPatientCpf(String cpf){
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientCpf(cpf);
//
//        if(medicalRecords.isEmpty()){
//            throw new BadRequestException("Nenhum prontuario referente a esse paciente");
//        }
//        List<SmallMedicalRecordDTO> smalLMedicalRecords = new ArrayList<>();
//        medicalRecords.forEach(medicalRecord -> {
//            smalLMedicalRecords.add(new SmallMedicalRecordDTO(medicalRecord.getId(), medicalRecord.getSignIn(), medicalRecord.getDate(), medicalRecord.getStaff()));
//        });
//        return smalLMedicalRecords;
//    }

    public List<MedicalRecord> getByStaffId(Integer id){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByStaffId(id);
        if(medicalRecords.isEmpty()){
            throw new BadRequestException("Nenhum prontuario registrado para esse staff");
        }
        return medicalRecords;
    }

}
