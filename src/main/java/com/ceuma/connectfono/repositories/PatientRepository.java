package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT * FROM patient WHERE type = 'ALUNO'", nativeQuery = true)
    List<Patient> findAllAlunos();

    @Query(value = "SELECT * FROM patient WHERE type = 'EXTERNO'", nativeQuery = true)
    List<Patient> findAllExterno();

    @Query(value = "SELECT * FROM patient WHERE cpf = :cpf", nativeQuery = true)
    Optional<Patient> findByCpf(@Param("cpf")String cpf);

}
