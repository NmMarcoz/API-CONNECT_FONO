package com.ceuma.connectfono.core.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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