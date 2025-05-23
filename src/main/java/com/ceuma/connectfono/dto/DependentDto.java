package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.Dependent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DependentDto {
    @JsonProperty("dependent")
    private Dependent dependent;
    @JsonProperty("patient_cpf")
    private String patientCpf;
}
