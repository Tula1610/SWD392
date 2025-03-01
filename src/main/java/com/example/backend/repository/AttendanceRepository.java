package com.example.backend.repository;

import com.example.backend.model.mongoDB.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findByEmployee_EmployeeID(String employeeID);
    List<Attendance> findByShifts_ShiftsID(String shiftsID);  
}