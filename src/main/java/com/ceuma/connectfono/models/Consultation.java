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

import java.util.List;
import java.util.UUID;

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
    private Integer id;

    @Column(name = "title")
    @Size(max = 12)
    @NotNull
    @NotBlank
    private String title;

    @Column(name = "description")
    @NotNull
    @NotBlank
    private String description;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public enum statusType{
        pendente,
        confirmada,
        cancelada,
        concluida
    }

}
