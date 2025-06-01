package com.ceuma.connectfono.core.interfaces;

import com.ceuma.connectfono.core.factories.PersonFactory;
import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.core.models.Person;

public interface PersonFactoryInterface {
    public Person getPerson(PersonFactory.PersonType type) throws BadRequestException;
}
