package com.example.backend.service;

import com.example.backend.dto.request.Employee.CreateEmployeeDTO;
import com.example.backend.dto.request.Employee.UpdateEmployeeDTO;
import com.example.backend.model.mongoDB.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(CreateEmployeeDTO employee);
    Employee updateEmployee(String employeeId, UpdateEmployeeDTO request);
    boolean deleteEmployee(String employeeID);

    Employee getEmployeeById(String employeeID);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
}
