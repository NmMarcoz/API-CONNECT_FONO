package com.ceuma.connectfono.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "staff")
@NoArgsConstructor
public class Staff extends Person{

    public Staff (Person obj){
        
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column (name = "password")
    @Size(min = 6)
    private String password;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "level")
    private Integer level;

}