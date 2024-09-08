package com.ceuma.connectfono.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvailableTimeAndDateDTO {
    private LocalDate dates;
    private List<Time> hours;


}
