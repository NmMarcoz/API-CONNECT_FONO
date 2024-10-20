package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    @Query(value = "SELECT * FROM medical_record WHERE patient_id = :patient_id", nativeQuery = true)
    List<MedicalRecord> getByPatientID(@Param("patient_id") UUID patientID);
}
