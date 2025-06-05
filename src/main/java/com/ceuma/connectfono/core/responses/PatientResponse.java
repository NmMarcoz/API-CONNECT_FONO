package com.ceuma.connectfono.core.responses;

import com.ceuma.connectfono.core.models.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientResponse {
    private final int status;
    private final String message;
    private Patient patient;
    private List<Patient> patients;

    public PatientResponse(int status, String message){
        this.message = message;
        this.status = status;
    }

    public PatientResponse(int status, String message, Patient patient){
        this.message = message;
        this.status = status;
        this.patient = patient;
    }
    public PatientResponse(int status, String message, Patient patient, List<Patient> patients){
        this.message = message;
        this.status = status;
        this.patient = patient;
        this.patients = patients;
    }

}
