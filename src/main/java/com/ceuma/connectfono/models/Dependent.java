package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dependent")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Dependent extends Person{

    @Column(name ="relationship")
    private String relationship;

}
