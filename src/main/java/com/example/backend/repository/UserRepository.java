package com.example.backend.repository;

import com.example.backend.enums.RoleName;
import com.example.backend.model.mongoDB.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    User findByEmailAndRole(String email, RoleName role);

    boolean existsByEmail(String email);

}