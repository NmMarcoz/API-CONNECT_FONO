package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DependentRepository extends JpaRepository<Dependent, UUID> {
    @Query(value = "SELECT * FROM dependent WHERE cpf = :cpf ", nativeQuery = true)
    Optional<Dependent> getByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM dependent WHERE patient_id = :patient_id", nativeQuery = true )
    List<Dependent> getByPatientId(@Param("patient_id") UUID patientId);
}
