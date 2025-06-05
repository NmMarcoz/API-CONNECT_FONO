package com.ceuma.connectfono.core.adapters;

import com.ceuma.connectfono.core.decorators.ConcretePersonDecorator;
import com.ceuma.connectfono.core.models.ConcretePerson;
import com.ceuma.connectfono.core.models.Patient;
import com.ceuma.connectfono.core.models.Person;
import com.ceuma.connectfono.core.models.Staff;

public class PersonAdapter {
    
    public ConcretePerson getPerson(Staff staff){
        return new ConcretePerson(staff.getId(), staff.getName(), staff.getPhone_number(), staff.getEmail());
    }
    public ConcretePerson getPerson(Patient patient){
        return new ConcretePerson(patient.getId(), patient.getName(), patient.getPhone_number(), patient.getEmail());
    }

    //TODO -> aplicação do decorator
    public ConcretePersonDecorator personDecorator(Staff person){
        return new ConcretePersonDecorator(this.getPerson(person));
    }
    public ConcretePersonDecorator personDecorator(Patient person){
        return new ConcretePersonDecorator(this.getPerson(person));
    }
}
