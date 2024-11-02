package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.Staff;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmallMedicalRecordDTO {
    @JsonProperty(value = "id")
    private UUID id;

    @JsonProperty(value = "sign_in")
    private String signIn;

    @JsonProperty(value = "date")
    private LocalDate date;

    private Staff staff;

}
