package com.ceuma.connectfono.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    @Size(max = 12)
    @NotNull
    @NotBlank
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull
    @NotBlank
    private String description;

    @ManyToOne
    @NotBlank
    private Patient patient;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "schedule_id")
//    @JsonManagedReference
//
//    private Schedule schedule;

    @Column(name = "status")
    private String status;


    public enum statusType{
        pendente,
        confirmada,
        cancelada,
        concluida
    }

}
