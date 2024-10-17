package com.ceuma.connectfono.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;


@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
//@Table(name = "person")
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "phone_number", unique = true)
    @NotNull
    private String phoneNumber;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;
}
