package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.ConsultationRequestDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Consultation;
import com.ceuma.connectfono.models.Hour;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.services.ConsultationService;
import com.ceuma.connectfono.services.ScheduleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    ConsultationService consultationService;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("")
    public ResponseEntity<Consultation> create(@RequestBody ConsultationRequestDTO requestDTO) {
        Consultation obj = requestDTO.getConsultation();

        if (obj == null) {
            throw new BadRequestException("Request inválida");
        }
        if (
                obj.getTitle() == null || obj.getTitle().isEmpty() ||
//                        obj.getSchedule() == null ||
                        obj.getPatient() == null
        ) {
            throw new BadRequestException("campos obrigatórios não informados");
        }
        // Pra implementar depois a função de verificar horas disponíveis
        List<Consultation> consultations = this.consultationService.getAll();

        Schedule schedule = new Schedule();
        schedule.setPatient(requestDTO.getConsultation().getPatient());
        schedule.setConsultation(requestDTO.getConsultation());
        schedule.setDate(requestDTO.getDate());
        schedule.setHour(requestDTO.getHour());


        this.consultationService.create(obj);
        Schedule savedSchedule = this.scheduleService.create(schedule);
        obj.setSchedule(savedSchedule);
        updateConsutation(obj, obj.getId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllConsultations() {
        List<Consultation> consultations = this.consultationService.getAll();
        return ResponseEntity.ok().body(consultations);
    }

    @PostMapping("/consultation/{id}")
    public ResponseEntity<Object> updateConsutation(@RequestBody Consultation obj, @PathVariable Long id){
        Consultation newConsultation = this.consultationService.update(obj, id);
        return ResponseEntity.ok().body(newConsultation);

    }
}
