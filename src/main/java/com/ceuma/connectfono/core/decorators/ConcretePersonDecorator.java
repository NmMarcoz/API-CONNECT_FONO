package com.ceuma.connectfono.core.decorators;

import com.ceuma.connectfono.core.models.ConcretePerson;

public class ConcretePersonDecorator {
    private final ConcretePerson person;
    public ConcretePersonDecorator(ConcretePerson person) {
        this.person = person;
    }
    public void showInfo(){
        System.out.println("____________LOG__________");
        this.person.showInfo();
        System.out.println("____________LOG__________");
    }

}
