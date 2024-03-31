package com.legit.housing.controller;

import com.legit.housing.dto.request.JwtReq;
import com.legit.housing.dto.request.UserRegisterDto;
import com.legit.housing.dto.response.JwtRes;
import com.legit.housing.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "token", produces = "application/json")
    public JwtRes token(@RequestBody @Valid JwtReq jwtReq) {
        return authenticationService.login(jwtReq.getUsername(), jwtReq.getPassword());
    }
    @PostMapping(path = "register", produces = "application/json")
    public JwtRes register(@RequestBody @Valid UserRegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }
}
