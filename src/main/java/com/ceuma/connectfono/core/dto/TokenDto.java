package com.ceuma.connectfono.core.dto;

import com.ceuma.connectfono.core.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class TokenDto {
    private String token;
    private Staff staff;
}
