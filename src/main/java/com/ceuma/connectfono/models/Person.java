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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "person")
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 100)
    private String name;
    @Column(name = "cpf", unique = true)
    @NotNull
    @Size(max = 14)
    private String cpf;
    @Column(name = "phoneNumber", unique = true)
    @NotNull
    private String phoneNumber;
    @Column(name = "email", unique = true)
    @NotNull
    private String email;
    @Column(name = "birthYear")
    @NotNull
    private Date birthYear;
    @Column(name = "type")
    @NotNull
    private String type;
}
