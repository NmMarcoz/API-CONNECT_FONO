package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query(value = "SELECT * FROM consultation WHERE  patient_id = :id", nativeQuery = true)
    List <Consultation> getByPatientId(@Param("id") long id);
}
