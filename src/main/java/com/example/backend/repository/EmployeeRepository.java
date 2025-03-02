package com.example.backend.repository;

import com.example.backend.model.mongoDB.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmail(String email);

    boolean existsByEmail(String email);
}
