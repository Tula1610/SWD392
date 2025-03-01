package com.example.backend.model;

import com.example.backend.model.mongoDB.Application;
import com.example.backend.model.mongoDB.Employee;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeApplication {

     int eaid;

     Employee employee;

     Application application;

     Resignation resignation;
}