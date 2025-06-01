package com.ceuma.connectfono.core.factories;

import java.util.Optional;



import com.ceuma.connectfono.core.interfaces.PersonFactoryInterface;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.models.Person;
import com.ceuma.connectfono.models.Staff;

public class PersonFactory implements PersonFactoryInterface {
    public enum PersonType{
        PATIENT,
        STAFF
    }
    
    public PersonFactory(){

    }

    public Person getPerson(PersonType type) throws BadRequestException {
        switch(type){
            case PATIENT:
                return new Patient();
            case STAFF:
                return new Staff();
            default:
                throw new BadRequestException("tipo deve ser STAFF ou PERSON");
        }
    }
}
