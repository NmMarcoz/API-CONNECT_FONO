package com.ceuma.connectfono.services;

import com.ceuma.connectfono.core.dto.MedicalRecordDTO;
import com.ceuma.connectfono.core.dto.SmallMedicalRecordDTO;
import com.ceuma.connectfono.core.dto.SmallPatientDTO;
import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.core.models.FonoEvaluation;
import com.ceuma.connectfono.core.models.MedicalHistory;
import com.ceuma.connectfono.core.models.MedicalRecord;
import com.ceuma.connectfono.core.models.Questions;
import com.ceuma.connectfono.repositories.FonoEvaluationRepository;
import com.ceuma.connectfono.repositories.MedicalHistoryRepository;
import com.ceuma.connectfono.repositories.MedicalRecordRepository;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import com.ceuma.connectfono.utils.NullPropertyUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    @Autowired
    private QuestionsRepository questionsRepository;

    @Transactional
    public MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO) {

        MedicalRecord medicalRecord = medicalRecordDTO.getMedicalRecord();
        FonoEvaluation fonoEvaluation = medicalRecordDTO.getFonoEvaluation();

        MedicalHistory medicalHistory = medicalRecordDTO.getMedicalHistory();
        List<Questions> questionsList = medicalRecordDTO.getQuestions();

        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);
        questionsList.forEach(questions -> questions.setMedicalHistory(medicalHistorySaved));

        medicalRecord.setFonoEvaluation(fonoEvaluation);
        medicalRecord.setMedicalHistory(medicalHistorySaved);
        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        FonoEvaluation fonoEvaluationSaved = fonoEvaluationRepository.save(fonoEvaluation);
        List<Questions> questionsSaved = questionsService.createLot(questionsList);

        medicalHistory.setMedicalRecord(medicalRecord);
        medicalHistory.setQuestions(questionsList);
        MedicalRecordDTO medicalRecordDTOSaved = new MedicalRecordDTO(medicalRecordSaved, fonoEvaluationSaved, medicalHistorySaved, questionsSaved);

        return medicalRecordDTOSaved;
    }

    public MedicalRecord createv2(MedicalRecord medicalRecord) {
        MedicalHistory medicalHistory = medicalRecord.getMedicalHistory();
        List<Questions> questions = medicalHistory.getQuestions();
        FonoEvaluation fonoEvaluation = medicalRecord.getFonoEvaluation();

        medicalHistory.setQuestions(null);
        MedicalHistory medicalHistorySaved = medicalHistoryService.create(medicalHistory);
        log.info("MedicalHistory criado: {}", medicalHistorySaved);
        questions.forEach(question -> {
            question.setMedicalHistory(medicalHistorySaved);
            log.info("Atualizando questions");
        });
        questionsService.createLot(questions);

        FonoEvaluation fonoEvaluationSaved = fonoEvaluationRepository.save(fonoEvaluation);
        log.info("Fono evaluation criado");
        medicalHistorySaved.setQuestions(questions);
        log.info("Perguntas adicioonadas");
        medicalRecord.setFonoEvaluation(fonoEvaluationSaved);
        log.info("Avaliação adicionada ao prontuário");
        medicalRecord.setMedicalHistory(medicalHistorySaved);
        log.info("Histórico clínico adicionadi ao prontuário");

        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        log.info("Prontuário criado");

        return medicalRecord;

    }

    @Transactional
    public MedicalRecord update(Long id, MedicalRecord medicalRecord) {
        // Buscar os registros existentes
        MedicalRecord existingRecord = medicalRecordRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Prontuário não encontrado para o ID " + id)
        );

        if(medicalRecord.getMedicalHistory() != null){
            MedicalHistory existingMedicalHistory = medicalHistoryRepository.findById(existingRecord.getMedicalHistory().getId()).orElseThrow();
            MedicalHistory medicalHistory = medicalRecord.getMedicalHistory();
            NullPropertyUtils.copyNonNullProperties(medicalHistory, existingMedicalHistory);
            log.info("MedicalHistory antes do update");
            MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(existingMedicalHistory);
            log.info("MedicalHistory atualizado: {}", savedMedicalHistory.getId());
            List<Questions> updatedQuestions = new ArrayList<>();
            existingMedicalHistory.getQuestions().forEach(question -> {
                log.info("Atualizando questions: {}", question.getTitle());
                Questions existingQuestion = questionsRepository.findById(question.getId()).orElseThrow();
                if(question.getTitle() != null){
                    existingQuestion.setTitle(question.getTitle());
                }
                if(question.getAnswer() != null){
                    existingQuestion.setAnswer(question.getAnswer());
                }
                existingQuestion.setMedicalHistory(existingRecord.getMedicalHistory());
                questionsRepository.save(existingQuestion);
                updatedQuestions.add(existingQuestion);
            });
            existingRecord.getMedicalHistory().setQuestions(updatedQuestions);
        }else{
            medicalRecord.setMedicalHistory(medicalHistoryRepository.findByMedicalRecordId(existingRecord.getMedicalHistory().getId()));
        }


        // Salvar o FonoEvaluation, se houver alterações
        if(medicalRecord.getFonoEvaluation() != null){
            FonoEvaluation existingFonoEvaluation = fonoEvaluationRepository.findById(existingRecord.getFonoEvaluation().getId()).orElseThrow();
            NullPropertyUtils.copyNonNullProperties(medicalRecord.getFonoEvaluation(), existingFonoEvaluation);
            FonoEvaluation savedFonoEvaluation = fonoEvaluationRepository.save(existingFonoEvaluation);
            log.info("Avaliação atualizada {}", savedFonoEvaluation.getId());
        }

        // Salvar o prontuário atualizado
        medicalRecordRepository.save(existingRecord);
        MedicalRecord result = medicalRecordRepository.getReferenceById(id);
        log.info("Medical record atualizado", result.getMedicalHistory());
        return existingRecord;
    }

    public List<MedicalRecord> getAll() {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAll();
        if (medicalRecords.isEmpty()) {
            throw new BadRequestException("Nenhum prontuário cadastrado");
        }

        return medicalRecords;
    }

    public MedicalRecord getById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Nenhum prontuario com esse id"));
        return medicalRecord;
    }

    public List<MedicalRecord> getByPatientId(Long id) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientID(id);
        if (medicalRecords.isEmpty()) {
            throw new BadRequestException("Nenhum prontuário referente a esse paciente");
        }
        return medicalRecords;
    }

    public List<SmallMedicalRecordDTO> getByStaffCpf(String cpf) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByStaffCpf(cpf);
        if (medicalRecords.isEmpty()) {
            throw new BadRequestException("Nenhum prontuario cadastrado por esse staff");
        }
        List<SmallMedicalRecordDTO> smallMedicalRecordDTO = new ArrayList<>();
        medicalRecords.forEach(medicalRecord -> {
            SmallPatientDTO smallPatientDTO = new SmallPatientDTO(medicalRecord.getPatient().getName(), medicalRecord.getPatient().getCpf());
            smallMedicalRecordDTO.add(
                    new SmallMedicalRecordDTO(
                            medicalRecord.getId(),
                            medicalRecord.getSignIn(),
                            medicalRecord.getDate(),
                            medicalRecord.getStaff(),
                            smallPatientDTO
                    ))
            ;
        });

        return smallMedicalRecordDTO;
    }

    public List<SmallMedicalRecordDTO> getByPatientCpf(String cpf) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByPatientCpf(cpf);

        if (medicalRecords.isEmpty()) {
            throw new BadRequestException("Nenhum prontuario referente a esse paciente");
        }
        List<SmallMedicalRecordDTO> smalLMedicalRecords = new ArrayList<>();
        medicalRecords.forEach(medicalRecord -> {
            SmallPatientDTO smallPatientDTO = new SmallPatientDTO(medicalRecord.getPatient().getName(), medicalRecord.getPatient().getCpf());
            smalLMedicalRecords.add(
                    new SmallMedicalRecordDTO(
                            medicalRecord.getId(),
                            medicalRecord.getSignIn(),
                            medicalRecord.getDate(),
                            medicalRecord.getStaff(),
                            smallPatientDTO
                    ))
            ;
        });
        return smalLMedicalRecords;
    }

    public List<MedicalRecord> getByStaffId(Long id) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getByStaffId(id);
        if (medicalRecords.isEmpty()) {
            throw new BadRequestException("Nenhum prontuario registrado para esse staff");
        }
        return medicalRecords;
    }

}
