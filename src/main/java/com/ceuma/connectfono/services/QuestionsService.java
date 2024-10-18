package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Questions;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionsService {
    @Autowired
    public QuestionsRepository questionsRepository;


    public Questions create(Questions question){
        question.setId(null);
        return questionsRepository.save(question);
    }

    public List<Questions> createLot(List<Questions> questions){
        questions.forEach(question -> {
            question.setId(null);
        });
        return questionsRepository.saveAll(questions);
    }

    public Questions findById(UUID id){
        return questionsRepository.findById(id).orElseThrow(()-> new BadRequestException("Question not found"));
    }

    public List<Questions> findAll(){
        List<Questions> questions = questionsRepository.findAll();
        if(questions.isEmpty()){
            throw new BadRequestException("Questions not found");
        }
        return questions;
    }
    public Questions update(Questions question){
        return questionsRepository.save(question);
    }

    public void delete(UUID id){
        questionsRepository.deleteById(id);
    }


}
