package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.handlers.ErrorResponse;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.responses.PatientResponse;
import com.ceuma.connectfono.services.PatientService;
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



    @GetMapping("")
    public ResponseEntity<List<Patient>> findAll(){
        System.out.println("entrou no get all");
        try{
            List<Patient> patients = this.patientService.findAll();
            return ResponseEntity.ok().body(patients);
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    //depois trocar para Patient, pois ainda nao fiz os handlers
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        System.out.println("entrou");
        try{
            if(id == null){
                throw new BadRequestException("Id não pode ser nulo");
            }
            Patient patient = this.patientService.findById(id);
            if(patient == null){
                throw new BadRequestException("Não existe paciente com esse id");
            }
            return ResponseEntity.ok().body(patient);
        }catch(RuntimeException e){
            if(e instanceof BadRequestException){
                throw new BadRequestException(e.getMessage());
            }
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Um erro desconhecido aconteceu", e);
            //throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<Patient> create(@RequestBody Patient obj){
        System.out.println("Entrou");
        if(obj.getCpf() == null || obj.getName() == null || obj.getEmail() == null || obj.getPhoneNumber() == null || obj.getBirthYear() == null){
            throw new BadRequestException("Campos: CPF, Nome, Email, Numero de telefone e Data de Nascimento, são obrigatórios");
        }
        this.patientService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Patient obj, @PathVariable Long id){
        if(obj.getName() == null && obj.getEmail() == null && obj.getDependents() == null && obj.getPassword() == null){
            throw new BadRequestException("Insira pelo menos um dos campos: Nome, Email, Senha ou Dependentes");
        }
        Patient newPatient = this.patientService.update(obj, id);

        return ResponseEntity.ok().body(buildSuccessResponse(200, "paciente alterado com sucesso", HttpStatus.OK, newPatient));
    }



    // BUILDRESPONSES abaixo
    public ResponseEntity<Object> buildSuccessResponse(int status, String message, HttpStatus httpStatus, Patient patient){
        PatientResponse patientResponse = new PatientResponse(status, message, patient);
        return ResponseEntity.status(httpStatus).body(patientResponse);
    }
    public ResponseEntity<Object> buildSuccessResponse(int status, String message, HttpStatus httpStatus, Patient patient, List<Patient> patients){
        PatientResponse patientResponse = new PatientResponse(status, message);
        patientResponse.setPatients(patients);
        return ResponseEntity.status(httpStatus).body(patientResponse);
    }
    public ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message, Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
