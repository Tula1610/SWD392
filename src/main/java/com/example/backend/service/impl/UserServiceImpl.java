package com.example.backend.service.impl;

import com.example.backend.dto.request.User.CreateUserDTO;
import com.example.backend.dto.request.User.UpdateUserDTO;
import com.example.backend.enums.RoleName;
import com.example.backend.service.CounterService;
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

    @Autowired
    private CounterService counterService;

    // <editor-fold default state="collapsed" desc="createUser">
    @Override
    public User createUser(CreateUserDTO request) {
        User user = new User();

        // Get the current count of users and increment
        long nextSequence = userRepository.count() + 1;
        user.setUserID(String.format("%08d", nextSequence)); // "00000001", "00000002", etc.
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : RoleName.DRAFT);

        // Update counter to reflect the new highest sequence
        counterService.updateCounter("UserID",nextSequence);

        return userRepository.save(user);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="updateUser">
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

    // <editor-fold default state="collapsed" desc="deleteUser">
    @Override
    public boolean deleteUser(String userId) {
        try {
            // Delete the user by MongoDB _id
            User userToDelete = getUserById(userId);
            if (userToDelete == null) {
                return false;
            }
            userRepository.deleteById(userId);

            // Fetch all remaining users
            List<User> remainingUsers = userRepository.findAll();

            // Reassign userIDs sequentially starting from 1
            int newSequence = 1;
            for (User user : remainingUsers) {
                user.setUserID(String.format("%08d", newSequence));
                userRepository.save(user);
                newSequence++;
            }

            // Update the counter via CounterService
            counterService.updateCounter("userID", newSequence - 1);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getAllUsers">
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getUserById">
    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="findByEmail">
    @Override
    public User findByEmail(String email) {
        try{
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="findByUserID">
    @Override
    public User findByUserID(String userID) {
        return userRepository.findByUserID(userID); // Use new repository method
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="null">

    // </editor-fold>

}

