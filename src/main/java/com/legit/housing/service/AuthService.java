package com.legit.housing.service;

import org.springframework.security.core.Authentication;

import com.legit.housing.dto.response.JwtRes;

public interface AuthService {
    JwtRes generateToken(Authentication authentication);
}
