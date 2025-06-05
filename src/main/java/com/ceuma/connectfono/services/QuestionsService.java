package com.ceuma.connectfono.services;

import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.core.models.Questions;
import com.ceuma.connectfono.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Questions findById(Long id){
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

    public void delete(Long id){
        questionsRepository.deleteById(id);
    }


}
