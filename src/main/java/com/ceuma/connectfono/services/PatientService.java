package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.DependentRepository;
import com.ceuma.connectfono.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    public Patient findById(Long id){
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new BadRequestException(
                "Paciente não encontrado"
        ));

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
        newPatient.setPassword(obj.getPassword());

        this.patientRepository.save(newPatient);
        return newPatient;
    }



}
