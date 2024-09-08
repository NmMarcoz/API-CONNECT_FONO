package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.Consultation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationRequestDTO {
    @JsonProperty("consultation")
    private Consultation consultation;

    @JsonProperty("hour")
    private Time hour;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy-hh-mm")
    private Date date;

}
