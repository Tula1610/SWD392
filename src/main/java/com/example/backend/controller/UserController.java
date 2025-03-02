package com.example.backend.controller;

import com.example.backend.dto.request.User.CreateUserDTO;
import com.example.backend.dto.request.User.UpdateUserDTO;
import com.example.backend.dto.response.ResponseData;
import com.example.backend.enums.RoleName;
import com.example.backend.model.mongoDB.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseData getAllUsers() {
        List<User> users = userService.getAllUsers();

        // Create a new list to avoid modifying the original data
        List<User> renumberedUsers = new ArrayList<>();
        int newSequence = 1;
        for (User user : users) {
            // Create a copy of the user to avoid altering the database
            User renumberedUser = new User();
            renumberedUser.setId(user.getId());
            renumberedUser.setUserID(String.format("%08d", newSequence)); // Reassign userID
            renumberedUser.setFirstName(user.getFirstName());
            renumberedUser.setLastName(user.getLastName());
            renumberedUser.setEmail(user.getEmail());
            renumberedUser.setRole(user.getRole());
            renumberedUser.setManagedApplicationIds(user.getManagedApplicationIds());
            renumberedUsers.add(renumberedUser);
            newSequence++;
        }

        return new ResponseData(
                200, true,
                "User list fetched successfully",
                renumberedUsers, null, null);
    }

    @GetMapping("/admin")
    public ResponseData getAdminUsers() {
        List<User> users = userService.getAllUsers();

        // Filter for ADMIN role
        List<User> adminUsers = users.stream()
                .filter(user -> user.getRole() == RoleName.ADMIN)
                .collect(Collectors.toList());

        // Renumber the filtered list
        List<User> renumberedAdminUsers = new ArrayList<>();
        int newSequence = 1;
        for (User user : adminUsers) {
            User renumberedUser = new User();
            renumberedUser.setId(user.getId());
            renumberedUser.setUserID(String.format("%08d", newSequence)); // Reassign userID
            renumberedUser.setFirstName(user.getFirstName());
            renumberedUser.setLastName(user.getLastName());
            renumberedUser.setEmail(user.getEmail());
            renumberedUser.setRole(user.getRole());
            renumberedUser.setManagedApplicationIds(user.getManagedApplicationIds());
            renumberedAdminUsers.add(renumberedUser);
            newSequence++;
        }

        return new ResponseData(
                200, true,
                "Admin user list fetched successfully",
                renumberedAdminUsers, null, null);
    }

    @GetMapping("/{userID}")
    public User getUserById(@PathVariable String userID) {
        return userService.getUserById(userID);

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<List<User>> createUsers(@RequestBody List<CreateUserDTO> users) {
        List<User> createdUsers = new ArrayList<>();
        for (CreateUserDTO dto : users) {

            User newUser = userService.createUser(dto);
            createdUsers.add(newUser);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }
    @PutMapping("/{userID}")
    public ResponseEntity<ResponseData> updateUser(@PathVariable String userID,
                                                   @RequestBody UpdateUserDTO request) {
        try {
            User updatedUser = userService.updateUser(userID, request);
            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData(404, false, "User not found with ID: " + userID, null, null, null));
            }
            return ResponseEntity.ok(new ResponseData(200, true, "User updated successfully", updatedUser, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData(500, false, "Failed to update user: " + e.getMessage(), null, null, null));
        }
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
        return ResponseEntity.ok().build();
    }
}