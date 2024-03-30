package com.legit.housing.service.impl;

import com.legit.housing.repository.main.UserRepository;
import com.legit.housing.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOpt = repository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("%s not found!", username));
        }
        return userOpt.get();
    }
}
