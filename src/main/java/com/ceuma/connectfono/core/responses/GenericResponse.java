package com.ceuma.connectfono.core.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse {
    private int status;
    private String message;
}
