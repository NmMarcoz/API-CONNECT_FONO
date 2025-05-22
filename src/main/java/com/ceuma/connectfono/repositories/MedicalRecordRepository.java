package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.MedicalRecord;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query(value = "SELECT * FROM medical_record WHERE patient_id = :patient_id", nativeQuery = true)
    List<MedicalRecord> getByPatientID(@Param("patient_id") Integer patientID);

    @Query(value = "SELECT * FROM medical_record WHERE patient_id = (SELECT id FROM patient WHERE cpf = :cpf)", nativeQuery = true)
    List<MedicalRecord> getByPatientCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM medical_record WHERE staff_id = :id", nativeQuery = true)
    List<MedicalRecord> getByStaffId(@Param("id") Integer staffId);
}
