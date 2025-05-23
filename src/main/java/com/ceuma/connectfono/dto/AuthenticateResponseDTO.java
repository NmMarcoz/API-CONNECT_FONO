package com.ceuma.connectfono.dto;

import com.ceuma.connectfono.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponseDTO {
    private String token;
    private String message;
    private Boolean auth;
    private Staff staff;
}
