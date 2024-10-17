package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.ConsultationRequestDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Consultation;
import com.ceuma.connectfono.models.Hour;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.services.ConsultationService;
import com.ceuma.connectfono.services.PatientService;
import com.ceuma.connectfono.services.ScheduleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    ConsultationService consultationService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PatientService patientService;

    @PostMapping("")
    public ResponseEntity<Consultation> create(@RequestBody ConsultationRequestDTO requestDTO) {
        Consultation obj = requestDTO.getConsultation();
        LocalTime requestHour = requestDTO.getHour();

        if(requestDTO.getDate().getDayOfMonth() < LocalDate.now().getDayOfMonth()){
            throw new BadRequestException("Não é possível agendar uma consulta para o passado");
        }
        if (obj == null) {
            throw new BadRequestException("Request inválida");
        }
        if (obj.getTitle() == null || obj.getTitle().isEmpty() || obj.getPatient() == null) {
            throw new BadRequestException("campos obrigatórios não informados");
        }

        obj.setStatus("pendente");
        System.out.println("chegou aqui");

        if(obj.getTitle().length() > 12){
            throw new BadRequestException("tamanho inválido de titulo: 12 letras no máximo");
        }

        // Pra implementar depois a função de verificar horas disponíveis
        List<Schedule> schedules = this.scheduleService.findByDate(requestDTO.getDate());
        List<LocalTime> consultationsHours = new ArrayList<>();
        if(schedules != null){
            schedules.forEach(item -> consultationsHours.add(item.getHour()));
        }

        System.out.println("schedules" + schedules);

        System.out.println("horários das consultas: " + consultationsHours);

        List<LocalTime> availableHours = scheduleService.getAvailableHours(consultationsHours);
        System.out.println("Horas disponiveis para consultas nesse dia: " + availableHours);
        System.out.println("Hora vindo da request: " + requestDTO.getHour());


        if (!availableHours.contains(requestHour)) {
            throw new BadRequestException("Não é possível agendar uma consulta nesse horário");
        }

        //tenho que refatorar esse trechinho aqui do schedule, ta mto feio kkkk
        Schedule schedule = new Schedule();
        schedule.setPatient(requestDTO.getConsultation().getPatient());
        schedule.setDate(requestDTO.getDate());
        schedule.setHour(requestDTO.getHour());


        this.consultationService.create(obj);

        Schedule savedSchedule = this.scheduleService.create(schedule);

        updateConsutation(obj, obj.getId());

        Patient patient = this.patientService.findById(obj.getPatient().getId());
        patient.setCpf(null);
        this.patientService.update(patient, patient.getId());


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllConsultations() {
        List<Consultation> consultations = this.consultationService.getAll();
        return ResponseEntity.ok().body(consultations);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Consultation>> getConsultationByPatientId(@PathVariable UUID id) {
        if (id == null) {
            throw new BadRequestException("O campo id é obrigatório");
        }
        List<Consultation> consultation = consultationService.getByPatientId(id);
        return ResponseEntity.ok().body(consultation);
    }

    @PostMapping("/consultation/{id}")
    public ResponseEntity<Object> updateConsutation(@RequestBody Consultation obj, @PathVariable UUID id) {
        Consultation newConsultation = this.consultationService.update(obj, id);
        return ResponseEntity.ok().body(newConsultation);

    }
}
