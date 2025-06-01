package com.ceuma.connectfono.core.interfaces;

import com.ceuma.connectfono.core.factories.PersonFactory;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Person;

public interface PersonFactoryInterface {
    public Person getPerson(PersonFactory.PersonType type) throws BadRequestException;
}
