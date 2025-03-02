package com.example.backend.service;

import com.example.backend.dto.request.User.CreateUserDTO;
import com.example.backend.dto.request.User.UpdateUserDTO;
import com.example.backend.model.mongoDB.User;
import java.util.List;

public interface UserService {
    User createUser(CreateUserDTO user);
    User updateUser(String userId, UpdateUserDTO request);
    boolean deleteUser(String userID);


    User getUserById(String userID);
    User findByUserID(String userID);
    List<User> getAllUsers();
    User findByEmail(String email);


}