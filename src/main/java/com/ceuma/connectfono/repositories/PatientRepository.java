package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT * FROM patient WHERE type = 'ALUNO'", nativeQuery = true)
    List<Patient> findAllAlunos();

    @Query(value = "SELECT * FROM patient WHERE type = 'EXTERNO'", nativeQuery = true)
    List<Patient> findAllExterno();
}
