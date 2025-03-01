package com.example.backend.service.impl;

import com.example.backend.dto.request.CreateUserDTO;
import com.example.backend.dto.request.UpdateUserDTO;
import com.example.backend.enums.RoleName;
import com.example.backend.model.mongoDB.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // <editor-fold default state="collapsed" desc="createUser">
    @Override
    public User createUser(CreateUserDTO request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        return userRepository.save(user);
    }
    // </editor-fold>

    @Override
    public User getUserById(String userID) {
        return userRepository.findById(userID).orElse(null);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // <editor-fold default state="collapsed" desc="createUser">
    @Override
    public User updateUser(String userId, UpdateUserDTO request) {
        try{

            User user = getUserById(userId);
            if (user == null) {
                return null;
            }

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setRole(request.getRole());

            return userRepository.save(user);
        }
        catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    @Override
    public void deleteUser(String userID) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmailAndRole(String email, RoleName role) {
        return userRepository.findByEmailAndRole(email, role);
    }


}

