package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.DependentRepository;
import com.ceuma.connectfono.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DependentRepository dependentRepository;


    public List<Patient> findAll(){
        List<Patient> patients = this.patientRepository.findAll();
        if(patients == null){
            throw new BadRequestException("Não há pacientes cadastrados");
        }
        return patients;

    }

    public List<Patient> findAllAlunos(){
        List<Patient> patients = this.patientRepository.findAllAlunos();
        return patients;
    }

    public List<Patient> findAllComum(){
        List<Patient> patients = this.patientRepository.findAllExterno();
        return patients;
    }
    public Patient findById(Long id){
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new BadRequestException(
                "Paciente não encontrado"
        ));

    }

    public Patient findByCpf(String cpf){
        Optional<Patient> patient = patientRepository.findByCpf(cpf);
        return patient.orElseThrow(() -> new BadRequestException("Nenhum paciente com esse cpf"));
    }
    @Transactional
    public Patient create(Patient obj){
        obj.setId(null);
        this.patientRepository.save(obj);
        // caso a quantidade de dependentes no objeto seja maior que zero, ele já salva tudo no banco também
        if(obj.getDependents() != null && obj.getDependents().size() > 0){
            this.dependentRepository.saveAll(obj.getDependents());
        }
        return obj;
    }

    @Transactional
    public Patient update(Patient obj, Long id){
        System.out.println(obj.getCpf());
        Patient newPatient = findById(id);
//        if (obj.getCpf() != newPatient.getCpf()){
//            throw new BadRequestException("Não é possível alterar o CPF");
//        }
        if(obj.getCpf() != null){
            throw new BadRequestException("Não é possível alterar o cpf");
        }
        newPatient.setName(obj.getName());
        newPatient.setDependents(obj.getDependents());
        newPatient.setEmail(obj.getEmail());
        newPatient.setRa(obj.getRa());
        newPatient.setEducation_level(obj.getEducation_level());
        newPatient.setPhone_number(obj.getPhone_number());
        newPatient.setGender(obj.getGender());
        newPatient.setBirth_year(obj.getBirth_year());
        newPatient.setOccupation(obj.getOccupation());
        newPatient.setType(obj.getType());
        if(obj.getType().equals("EXTERNO")){
            newPatient.setRa(null);
            newPatient.setType(obj.getType());
        }

        this.patientRepository.save(newPatient);
        return newPatient;
    }



}