package com.legit.housing.service.impl;

import com.legit.housing.dto.request.UserRegisterDto;
import com.legit.housing.dto.response.JwtRes;
import com.legit.housing.entity.main.User;
import com.legit.housing.repository.main.UserRepository;
import com.legit.housing.service.AuthenticationService;
import com.legit.housing.service.TokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokeService tokeService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    public AuthenticationServiceImpl(UserRepository userRepository,
                                     TokeService tokeService) {
        this.userRepository = userRepository;
        this.tokeService = tokeService;
    }

    public JwtRes register(UserRegisterDto request) {

        if (userRepository.existsUserByUsername(request.getEmail())) {
            throw new DataIntegrityViolationException(String.format("User with %s already exists, please try a different email address", request.getEmail()));
        }

        var user = new User(request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstname(),
                request.getLastname(),
                request.getRole(),
                request.getActive()
        );

        userRepository.save(user);

        return login(request.getEmail(), request.getPassword());
    }

    public JwtRes login(String username, String password) {
        return tokeService.generateToken(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
    }
}
