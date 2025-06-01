package com.ceuma.connectfono.core.adapters;

import com.ceuma.connectfono.core.models.ConcretePerson;
import com.ceuma.connectfono.core.models.Patient;
import com.ceuma.connectfono.core.models.Staff;

public class PersonAdapter {
    
    public ConcretePerson getPerson(Staff staff){
        return new ConcretePerson(staff.getId(), staff.getName(), staff.getPhone_number(), staff.getEmail());
    }
    public ConcretePerson getPerson(Patient patient){
        return new ConcretePerson(patient.getId(), patient.getName(), patient.getPhone_number(), patient.getEmail());
    }
}
