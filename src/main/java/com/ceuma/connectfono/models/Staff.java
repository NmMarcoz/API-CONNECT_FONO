package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "staff")
@NoArgsConstructor
@EqualsAndHashCode
public class Staff extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "password", columnDefinition = "TEXT")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Column(name = "cpf", unique = true, columnDefinition = "TEXT")
    private String cpf;

    @Column(name = "level")
    private Integer level;

}
