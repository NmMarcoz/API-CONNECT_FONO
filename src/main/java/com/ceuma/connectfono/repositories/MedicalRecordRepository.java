package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

}
