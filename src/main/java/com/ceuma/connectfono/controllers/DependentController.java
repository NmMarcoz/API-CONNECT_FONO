package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.core.dto.DependentDto;
import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.core.models.Dependent;
import com.ceuma.connectfono.core.responses.GenericResponse;
import com.ceuma.connectfono.services.DependentService;
import com.ceuma.connectfono.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dependent")
public class DependentController {
    @Autowired
    DependentService dependentService;
    @Autowired
    private PatientService patientService;

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody DependentDto dependentDto) {
        Dependent dependent = new Dependent();
        if(dependentDto.getDependent() == null){
            throw new BadRequestException("Insira o dependente");
        }
        dependent = dependentDto.getDependent();
        if(dependent.getName() == null
        || dependent.getRelationship() == null
        || dependent.getPhone_number() == null)
        if(dependentDto.getPatientCpf() == null){
            throw new BadRequestException("Insira o CPF do paciente");
        }

        dependent.setPatient(patientService.findByCpf(dependentDto.getPatientCpf()));

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
    public ResponseEntity<List<Dependent>> getPatientById(@PathVariable("id") Long id) {
        List<Dependent> dependents = dependentService.getByPatientId(id);
        return ResponseEntity.ok().body(dependents);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Dependent dependent) {
        Dependent dependentUpdated = dependentService.update(id, dependent);
        return ResponseEntity.ok().body(buildGenericResponse(200, "dependente atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        dependentService.delete(id);
        return ResponseEntity.status(200).body(buildGenericResponse(200, "dependente deletado com sucesso"));
    }



    public GenericResponse buildGenericResponse(int status, String message){
        return new GenericResponse(status, message);
    }

}
