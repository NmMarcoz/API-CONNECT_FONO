package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Dependent;
import com.ceuma.connectfono.responses.GenericResponse;
import com.ceuma.connectfono.services.DependentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/dependent")
public class DependentController {
    @Autowired
    DependentService dependentService;

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Dependent dependent) {
        if(dependent.getName() == null
        || dependent.getPatient().getId() == null
        || dependent.getRelationship() == null
        || dependent.getPhone_number() == null
        || dependent.getCpf() == null){
            throw new BadRequestException("Os seguintes campos são obrigatórios: Nome, Cpf, Telefone, Relação e Id do paciente");
        }

        Dependent depedentSaved = dependentService.create(dependent);
        return ResponseEntity.status(201).body(buildGenericResponse(201, "dependente cadastrado com sucesso"));

    }
    @GetMapping("")
    public ResponseEntity<List<Dependent>> getAll() {
        System.out.println("ué");
        List<Dependent> dependents = dependentService.getAll();
        return ResponseEntity.ok().body(dependents);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Dependent>> getPatientById(@PathVariable("id") UUID id) {
        List<Dependent> dependents = dependentService.getByPatientId(id);
        return ResponseEntity.ok().body(dependents);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Dependent> getByCpf(@PathVariable String cpf){
        Dependent dependent = dependentService.getByCpf(cpf);
        return ResponseEntity.ok().body(dependent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody Dependent dependent) {
        Dependent dependentUpdated = dependentService.update(id, dependent);
        return ResponseEntity.ok().body(buildGenericResponse(200, "dependente atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        dependentService.delete(id);
        return ResponseEntity.status(204).body(buildGenericResponse(204, "dependente deletado com sucesso"));
    }



    public GenericResponse buildGenericResponse(int status, String message){
        return new GenericResponse(status, message);
    }

}