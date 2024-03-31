package com.legit.housing.service;

import com.legit.housing.dto.request.UserRegisterDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.legit.housing.dto.response.JwtRes;

public interface AuthenticationService {

    JwtRes register(UserRegisterDto request);
    JwtRes login(String username, String password);
}
