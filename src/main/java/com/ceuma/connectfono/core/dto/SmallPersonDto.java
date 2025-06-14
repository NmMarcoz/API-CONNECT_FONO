package com.ceuma.connectfono.core.dto;

import com.ceuma.connectfono.core.factories.PersonFactory;
import com.ceuma.connectfono.core.factories.PersonFactory.PersonType;
import com.ceuma.connectfono.core.models.Person;

public class SmallPersonDto {
    public PersonType personType;
    public PersonFactory personFactory;
    public SmallPersonDto(PersonType personType){
        this.personType = personType;
        this.personFactory = new PersonFactory();
    };
    public Person person = personFactory.getPerson(personType);
    
}
