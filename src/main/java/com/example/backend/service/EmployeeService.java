package com.example.backend.service;

import com.example.backend.model.mongoDB.Employee;
import com.example.backend.enums.EmployeeStatus;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Optional<Employee> getEmployeeById(String employeeID);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByStatus(EmployeeStatus status);
    Employee updateEmployee(String employeeID, Employee employee);
    void deleteEmployee(String employeeID);
    Employee findByUsername(String username);
    Employee findByEmail(String email);
}