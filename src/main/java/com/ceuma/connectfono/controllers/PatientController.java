package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.handlers.ErrorResponse;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.PatientRepository;
import com.ceuma.connectfono.responses.GenericResponse;
import com.ceuma.connectfono.responses.PatientResponse;
import com.ceuma.connectfono.services.PatientService;
import com.ceuma.connectfono.utils.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/patient")
@Validated
public class PatientController {
    @Autowired
    private PatientService patientService;

    private final StringUtils stringUtils = new StringUtils();
    @Autowired
    private PatientRepository patientRepository;

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

    @GetMapping("/externos")
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
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
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

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> findByCpf(@PathVariable String cpf){
        Patient patient = this.patientService.findByCpf(cpf);
        return ResponseEntity.ok().body(patient);
    }
    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Patient obj) {

        if (
                stringUtils.isNullOrEmpty(obj.getCpf()) ||
                        stringUtils.isNullOrEmpty(obj.getName())||
                        stringUtils.isNullOrEmpty(obj.getEmail()) ||
                        stringUtils.isNullOrEmpty(obj.getType())){
            throw new BadRequestException("Campos: CPF, Nome, Email e Tipo do Paciente são obrigatórios");
        }
        obj.setType(obj.getType().toUpperCase());
        if ((obj.getRa() == null) && obj.getType().equals("ALUNO")) {
            throw new BadRequestException("Para alunos, o RA é obrigatório");
        }

        System.out.println(obj.getType());
        if (!obj.getType().equals("ALUNO") && !obj.getType().equals("EXTERNO")) {
            throw new BadRequestException("Tipo de paciente inválido. Válidos somente ALUNO ou EXTERNO");
        }

        obj.setGender(obj.getGender().toUpperCase());
        if(!obj.getGender().equalsIgnoreCase("M") && !obj.getGender().equalsIgnoreCase("F")) {
            throw new BadRequestException("Gênero só pode ser M ou F");
        }

        this.patientService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(buildSuccessResponse(201, "Usuário cadastrado"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Patient obj, @PathVariable Integer id) {
        if (obj.getName() == null && obj.getEmail() == null && obj.getDependents() == null) {
            throw new BadRequestException("Insira pelo menos um dos campos: Nome, Email ou Dependentes");
        }
        Patient newPatient = this.patientService.update(obj, id);

        return ResponseEntity.ok().body(buildSuccessResponse(200, "paciente alterado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        Patient patient = this.patientService.findById(id);
        if(patient == null){
            throw new BadRequestException("O paciente não existe");
        }
        patientRepository.delete(patient);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "paciente deletado com sucesso"));
    }

    // BUILDRESPONSES abaixo
    public GenericResponse buildSuccessResponse(int status, String message) {
        GenericResponse genericResponse = new GenericResponse(status, message);
        return genericResponse;
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
