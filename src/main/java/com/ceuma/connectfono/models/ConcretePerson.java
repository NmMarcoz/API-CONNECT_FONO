package com.ceuma.connectfono.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@NoArgsConstructor
public class ConcretePerson extends Person {
    public ConcretePerson(Long id, String name, String phone_number, String email){
        this.setName(name);
        this.setId(id);
        this.setEmail(email);
        this.setPhone_number(phone_number);
    }
}
