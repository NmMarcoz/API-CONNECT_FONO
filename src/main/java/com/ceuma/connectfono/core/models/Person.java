package com.ceuma.connectfono.core.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@Table(name = "person")
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "phone_number", unique = true)
    @NotNull
    private String phone_number;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    public void showInfo(){
        System.out.println("Nome" + this.getName());
        System.out.println("Id" + this.getId());
        System.out.println("Email" + this.getEmail());
        System.out.println("Numero de telefone" + this.getPhone_number());
    }
}