package com.ceuma.connectfono.core.builders;

import com.ceuma.connectfono.core.interfaces.PatientBuilderInterface;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Consultation;
import com.ceuma.connectfono.models.Dependent;
import com.ceuma.connectfono.models.Patient;

import java.time.LocalDate;
import java.util.List;

public class PatientBuilder implements PatientBuilderInterface{
    private Patient patient = new Patient();
    public PatientBuilder(){
        this.patient = new Patient();
    }
    public void reset(){
        this.patient = new Patient();
    }

    public void setName(String name){
        this.patient.setName(name);
    }

    public void setPhoneNumber(String phoneNumber){
        this.patient.setPhone_number(phoneNumber);
    }

    public void setEmail(String email){
        this.patient.setEmail(email);
    }

    public void setCpf(String cpf){
        this.patient.setCpf(cpf);
    }

    public void setRa(String ra){
        this.patient.setRa(ra);
    }
    public void setBirthYear(LocalDate birthYear){
        this.patient.setBirth_year(birthYear);
    }

    public void setGender(char gender){
        if(gender != 'M' && gender != 'F'){
            throw new BadRequestException("O sexo deve ser masculino ou feminino");
        }
        this.patient.setGender(gender);
    }

    public void setOcupation(String occupation){
        this.patient.setOccupation(occupation);
    }

    public void setEducationLevel(String educationLevel){
        this.patient.setEducation_level(educationLevel);
    }

    public void setDependents(List<Dependent> dependents){
        this.patient.setDependents(dependents);
    }


    public void setConsultations(List<Consultation> consultations){
        this.patient.setConsultations(consultations);
    }

    public void setType(String type){
        this.patient.setType(type);
    }

    public void setAddress(String address){
        this.patient.setAddress(address);
    }

    public Patient build(){
        return this.patient;
    }

}
