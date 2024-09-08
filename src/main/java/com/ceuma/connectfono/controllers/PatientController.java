package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.handlers.ErrorResponse;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.responses.PatientResponse;
import com.ceuma.connectfono.services.PatientService;
import com.ceuma.connectfono.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/patient")
@Validated
public class PatientController {
    @Autowired
    private PatientService patientService;


    private final StringUtils stringUtils = new StringUtils();

    @GetMapping("")
    public ResponseEntity<List<Patient>> findAll() {
        System.out.println("entrou no get all");
        try {
            List<Patient> patients = this.patientService.findAll();
            return ResponseEntity.ok().body(patients);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<Patient>> findAllAlunos() {
        List<Patient> patients = this.patientService.findAllAlunos();
        if (patients.isEmpty()) {
            throw new BadRequestException("Não há alunos cadastrados");
        }
        return ResponseEntity.ok().body(patients);

    }

    @GetMapping("externos")
    public ResponseEntity<List<Patient>> findAllExterno() {
        //tirei o try e catch pq tem um global exception handler pegando as exceções
        List<Patient> patients = this.patientService.findAllComum();
        if (patients.size() == 0) {
            throw new BadRequestException("Não há pacientes externos cadastrados");
        }
        return ResponseEntity.ok().body(patients);

    }

    //depois trocar para Patient, pois ainda nao fiz os handlers
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        System.out.println("entrou");
        if (id == null) {
            throw new BadRequestException("Id não pode ser nulo");
        }
        Patient patient = this.patientService.findById(id);
        if (patient == null) {
            throw new BadRequestException("Não existe paciente com esse id");
        }
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Patient obj) {

        if (
                stringUtils.isNullOrEmpty(obj.getCpf()) ||
                        stringUtils.isNullOrEmpty(obj.getName())||
                        stringUtils.isNullOrEmpty(obj.getEmail()) ||
                        stringUtils.isNullOrEmpty(obj.getType()) ||
                        stringUtils.isNullOrEmpty(obj.getPassword())
        ) {
            throw new BadRequestException("Campos: CPF, Nome, Email, Senha e Tipo do Paciente são obrigatórios");
        }
        obj.setType(obj.getType().toUpperCase());
        if ((obj.getRa() == null) && obj.getType().equals("ALUNO")) {
            throw new BadRequestException("Para alunos, o RA é obrigatório");
        }

        System.out.println(obj.getType());
        if (!obj.getType().equals("ALUNO") && !obj.getType().equals("EXTERNO")) {
            throw new BadRequestException("Tipo de paciente inválido. Válidos somente ALUNO ou EXTERNO");
        }
        this.patientService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(buildSuccessResponse(201, "Usuário cadastrado", HttpStatus.CREATED, obj));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Patient obj, @PathVariable Long id) {
        if (obj.getName() == null && obj.getEmail() == null && obj.getDependents() == null && obj.getPassword() == null) {
            throw new BadRequestException("Insira pelo menos um dos campos: Nome, Email, Senha ou Dependentes");
        }
        Patient newPatient = this.patientService.update(obj, id);

        return ResponseEntity.ok().body(buildSuccessResponse(200, "paciente alterado com sucesso", HttpStatus.OK, newPatient));
    }


    // BUILDRESPONSES abaixo
    public ResponseEntity<Object> buildSuccessResponse(int status, String message, HttpStatus httpStatus, Patient patient) {
        PatientResponse patientResponse = new PatientResponse(status, message, patient);
        return ResponseEntity.status(httpStatus).body(patientResponse);
    }

    public ResponseEntity<Object> buildSuccessResponse(int status, String message, HttpStatus httpStatus, Patient patient, List<Patient> patients) {
        PatientResponse patientResponse = new PatientResponse(status, message);
        patientResponse.setPatients(patients);
        return ResponseEntity.status(httpStatus).body(patientResponse);
    }

    public ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message, Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
