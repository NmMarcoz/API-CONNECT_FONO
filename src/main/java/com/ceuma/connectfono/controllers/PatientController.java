package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.services.PatientService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/patient")
@Validated
public class PatientController {
    @Autowired
    private PatientService patientService;


    //depois trocar para Patient, pois ainda nao fiz os handlers
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        System.out.println("entrou");
        try{
            if(id == null){
                throw new RuntimeException("Id não pode ser nulo");
            }
            Patient patient = this.patientService.findById(id);
            if(patient == null){
                throw new RuntimeException("Não existe paciente com esse id");
            }
            return ResponseEntity.ok().body(patient);
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
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
}
