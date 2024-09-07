package com.ceuma.connectfono.services;


import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Consultation;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.ConsultationRepository;
import com.ceuma.connectfono.repositories.HourRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {
    @Autowired
    ConsultationRepository consultationRepository;
    @Transactional
    public Consultation create(Consultation obj){
        obj.setId(null);
        this.consultationRepository.save(obj);
        return obj;
    }

    public List<Consultation> getAll(){
        List<Consultation> consultations = this.consultationRepository.findAll();
        if(consultations == null){
            throw new BadRequestException("Não há consultas cadastradas");
        }
        return consultations;
    }

    public Consultation getById(Long id){
        Optional<Consultation> consultation = this.consultationRepository.findById(id);
        return consultation.orElseThrow(() -> new BadRequestException("Não há consultas cadastradas com esse id"));
    }

    @Transactional
    public Consultation update(Consultation obj, Long id){
        Consultation newConsultation = getById(id);

        newConsultation.setSchedule(obj.getSchedule());
        newConsultation.setTitle(obj.getTitle());
        newConsultation.setDescription(obj.getDescription());

        this.consultationRepository.save(newConsultation);
        return newConsultation;
    }

}
