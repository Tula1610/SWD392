package com.example.backend.model;

import com.example.backend.enums.EmployeeShiftsStatus;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.model.mongoDB.Shift;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeShifts {

     int esid;

     Employee employee;

     Shift shift;

     EmployeeShiftsStatus status;
}