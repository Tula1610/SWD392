package com.example.backend.service;

import com.example.backend.dto.request.CreateUserDTO;
import com.example.backend.dto.request.UpdateUserDTO;
import com.example.backend.model.mongoDB.User;
import java.util.List;

public interface UserService {
    User createUser(CreateUserDTO user);
    User updateUser(String userId, UpdateUserDTO request);
    void deleteUser(String userID);


    User getUserById(String userID);
    List<User> getAllUsers();
    User findByEmail(String email);


}