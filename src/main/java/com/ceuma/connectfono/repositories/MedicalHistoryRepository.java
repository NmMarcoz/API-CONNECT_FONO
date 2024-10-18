package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, UUID> {

}
