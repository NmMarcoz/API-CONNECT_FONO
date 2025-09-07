package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.core.adapters.PersonAdapter;
import com.ceuma.connectfono.core.builders.PatientBuilder;
import com.ceuma.connectfono.core.decorators.ConcretePersonDecorator;
import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.handlers.ErrorResponse;
import com.ceuma.connectfono.core.models.Patient;
import com.ceuma.connectfono.repositories.PatientRepository;
import com.ceuma.connectfono.core.responses.GenericResponse;
import com.ceuma.connectfono.core.responses.PatientResponse;
import com.ceuma.connectfono.services.PatientService;
import com.ceuma.connectfono.utils.StringUtils;
import jakarta.transaction.Transactional;
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
@Transactional
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

        obj.setGender(Character.toUpperCase(obj.getGender()));
        if(obj.getGender() != 'M' && obj.getGender() != 'F') {
            throw new BadRequestException("Gênero só pode ser M ou F");
        }
        PatientBuilder patientBuilder = getPatientBuilder(obj);
        //TODO -> aplicação do builder
        Patient buildedPatient = patientBuilder.build();

        PersonAdapter personAdapter = new PersonAdapter();
       //TODO -> APlicação do adapter
        ConcretePersonDecorator decoratedObj = personAdapter.personDecorator(buildedPatient);

        //TODO -> Aplicação do decorator
        decoratedObj.showInfo();

        this.patientService.create(buildedPatient);
        //tenho que saber por que exatamente tem que fazer esse fromcurrentrequest com uri.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(buildSuccessResponse(201, "Usuário cadastrado"));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Patient obj, @PathVariable Long id) {
        if (obj.getName() == null && obj.getEmail() == null && obj.getDependents() == null) {
            throw new BadRequestException("Insira pelo menos um dos campos: Nome, Email ou Dependentes");
        }
        Patient newPatient = this.patientService.update(obj, id);

        return ResponseEntity.ok().body(buildSuccessResponse(200, "paciente alterado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        patientService.delete(id);
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

    private static PatientBuilder getPatientBuilder(Patient obj) {
        PatientBuilder patientBuilder = new PatientBuilder();

        patientBuilder.setName(obj.getName());
        patientBuilder.setCpf(obj.getCpf());
        patientBuilder.setEmail(obj.getEmail());
        patientBuilder.setGender(obj.getGender());
        patientBuilder.setType(obj.getType());
        patientBuilder.setRa(obj.getRa());
        patientBuilder.setDependents(obj.getDependents());
        patientBuilder.setAddress(obj.getAddress());
        patientBuilder.setBirthYear(obj.getBirth_year());
        patientBuilder.setConsultations(obj.getConsultations());
        patientBuilder.setOcupation(obj.getOccupation());
        patientBuilder.setEducationLevel(obj.getEducation_level());
        patientBuilder.setPhoneNumber(obj.getPhone_number());

        return patientBuilder;
    }
}
