package com.example.backend.controller;

import com.example.backend.dto.request.CreateUserDTO;
import com.example.backend.dto.request.UpdateUserDTO;
import com.example.backend.model.mongoDB.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userID}")
    public User getUserById(@PathVariable String userID) {
        return userService.getUserById(userID);

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("/{userID}")
    public User updateUser(@PathVariable String userID,
                           @RequestBody UpdateUserDTO request) {
        try {
            User user = userService.updateUser(userID, request);
            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
        return ResponseEntity.ok().build();
    }
}