package com.ceuma.connectfono.core.models;

import lombok.NoArgsConstructor;



//TODO Decorator
@NoArgsConstructor
public class ConcretePerson extends Person {
    public ConcretePerson(Long id, String name, String phone_number, String email){
        this.setName(name);
        this.setId(id);
        this.setEmail(email);
        this.setPhone_number(phone_number);
    }
}
