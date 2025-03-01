package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean checkLogin(LoginDTO request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }


}