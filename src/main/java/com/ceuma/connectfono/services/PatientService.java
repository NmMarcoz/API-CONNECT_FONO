package com.ceuma.connectfono.services;

import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.DependentRepository;
import com.ceuma.connectfono.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DependentRepository dependentRepository;

    public Patient findById(Long id){
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new RuntimeException(
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

}
