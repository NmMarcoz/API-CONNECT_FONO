package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
