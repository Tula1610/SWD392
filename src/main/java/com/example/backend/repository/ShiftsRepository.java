package com.example.backend.repository;

import com.example.backend.model.Shifts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShiftsRepository extends MongoRepository<Shifts, String> {
    List<Shifts> findByEmployee_EmployeeID(String employeeID); 
    List<Shifts> findByCreatedBy_UserID(String userID);  
}