package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.Consultation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationRequestDTO {
    @JsonProperty("consultation")
    private Consultation consultation;

    @JsonProperty("hour")
    private LocalTime hour;

    @JsonProperty("date")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

}
