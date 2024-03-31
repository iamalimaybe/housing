package com.legit.housing.service;

import com.legit.housing.dto.response.JwtRes;
import org.springframework.security.core.Authentication;

public interface TokeService {
    JwtRes generateToken(Authentication authentication);
}
