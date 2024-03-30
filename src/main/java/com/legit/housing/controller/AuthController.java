package com.legit.housing.controller;

import com.legit.housing.dto.request.JwtReq;
import com.legit.housing.dto.response.JwtRes;
import com.legit.housing.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(path = "token", produces = "application/json")
    public JwtRes token(@RequestBody @Valid JwtReq jwtReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtReq.getUsername(), jwtReq.getPassword())
        );

        return authService.generateToken(authentication);
    }
}
