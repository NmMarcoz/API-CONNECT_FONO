package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Dependent;
import com.ceuma.connectfono.repositories.DependentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DependentService {
    @Autowired
    DependentRepository dependentRepository;

    @Transactional
    public Dependent create(Dependent dependent) {
        return dependentRepository.save(dependent);
    }

    public Dependent getById(Long id) {
        return dependentRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Não existe nenhum dependente com esse id")
        );
    }

    public List<Dependent> getAll() {
        List<Dependent> dependents = dependentRepository.findAll();
        if (dependents.isEmpty()) {
            throw new BadRequestException("Não há nenhum dependente cadastrado");
        }
        return dependents;
    }

    public List<Dependent> getByPatientId(Long patientId) {
        List<Dependent> dependents = dependentRepository.getByPatientId(patientId);
        if (dependents.isEmpty()) {
            throw new BadRequestException("Nenhum dependente cadastrado para esse paciente");
        }
        return dependents;
    }

    public Dependent update(Long id, Dependent dependent) {
        Dependent newDependent = getById(id);
        if (newDependent == null) {
            throw new BadRequestException("Nenhum dependente com esse id cadastrado");
        }
        if (dependent.getName() != null) {
            newDependent.setName(dependent.getName());
        }
        if (dependent.getEmail() != null) {
            newDependent.setEmail(dependent.getEmail());
        }
        if (dependent.getRelationship() != null) {
            newDependent.setRelationship(dependent.getRelationship());
        }
        if (dependent.getPhone_number() != null) {
            newDependent.setPhone_number(dependent.getPhone_number());
        }
        if (dependent.getPatient() != null) {
            newDependent.setPatient(dependent.getPatient());
        }
        return dependentRepository.save(newDependent);

    }

    public void delete(Long id) {
        if (getById(id) == null) {
            throw new BadRequestException("Não existe nenhum dependente com esse id");
        }
        dependentRepository.deleteById(id);
        return;
    }
}
