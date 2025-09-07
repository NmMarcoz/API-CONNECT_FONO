package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.core.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query(value = "SELECT * FROM medical_record WHERE patient_id = :patient_id", nativeQuery = true)
    List<MedicalRecord> getByPatientID(@Param("patient_id") Long patientID);

    @Query(value = "SELECT * FROM medical_record WHERE patient_id = (SELECT id FROM patient WHERE cpf = :cpf)", nativeQuery = true)
    List<MedicalRecord> getByPatientCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM medical_record WHERE staff_id = :id", nativeQuery = true)
    List<MedicalRecord> getByStaffId(@Param("id") Long staffId);

    @Query(value = "SELECT * FROM medical_record WHERE staff_id = (SELECT id FROM staff where cpf = :cpf)", nativeQuery = true)
    List<MedicalRecord> getByStaffCpf(@Param("cpf") String cpf);

    @Modifying
    @Query(value = "DELETE FROM medical_record WHERE patient_id = :patient_id", nativeQuery = true)
    void deleteByPatientID(@Param("patient_id")Long patientID);
}
