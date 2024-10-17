package com.ceuma.connectfono.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@Table(name = "person")
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id;

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
}
