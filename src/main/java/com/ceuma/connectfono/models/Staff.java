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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column (name = "password")
    @Size(min = 6)
    private String password;

}
