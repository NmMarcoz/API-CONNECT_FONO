package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.core.Globals.GlobalVariables;
import com.ceuma.connectfono.core.dto.LoginRequestDto;
import com.ceuma.connectfono.core.dto.TokenDto;
import com.ceuma.connectfono.core.models.Staff;
import com.ceuma.connectfono.core.responses.AuthResponse;
import com.ceuma.connectfono.services.JsonWebTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequestDto loginRequest) {
        TokenDto response = jsonWebTokenService.generateToken(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().body(buildAuthResponse(response));
    }


    public AuthResponse buildAuthResponse(TokenDto response) {
        int expiresIn = GlobalVariables.TokenExpiresIn; // Token expiration time in seconds (1 hour)
        String message = "Autenticação bem-sucedida";
        return new AuthResponse(response.getToken(), expiresIn, message, response.getStaff());
    }

}

