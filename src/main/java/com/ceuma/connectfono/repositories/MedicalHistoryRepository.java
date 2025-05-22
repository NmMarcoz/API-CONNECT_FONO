package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    @Query(value = "SELECT * FROM medical_history WHERE medical_record_id = :medical_record_id", nativeQuery = true)
    MedicalHistory findByMedicalRecordId(@Param("medical_record_id") Long id);
}
