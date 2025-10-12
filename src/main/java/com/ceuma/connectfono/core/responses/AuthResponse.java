package com.ceuma.connectfono.core.responses;

import com.ceuma.connectfono.core.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private int expiresIn;
    private String message;
    Staff staff;


}
