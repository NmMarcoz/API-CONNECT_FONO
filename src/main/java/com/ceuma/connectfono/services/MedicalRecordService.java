package com.ceuma.connectfono.services;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.models.MedicalHistory;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Questions;
import com.ceuma.connectfono.repositories.MedicalRecordRepository;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalHistoryService medicalHistoryService;
    @Autowired
    QuestionsService questionsService;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

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

}
