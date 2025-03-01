package com.example.backend.model;

import com.example.backend.enums.EmployeeStatus;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.model.mongoDB.Restaurant;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRestaurant {

     int erid;

     Employee employee;

     Restaurant restaurant;

     EmployeeStatus status;
}