package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    @Query(value = "SELECT * FROM dependent WHERE patient_id = :patient_id", nativeQuery = true )
    List<Dependent> getByPatientId(@Param("patient_id") Long patientId);
}
