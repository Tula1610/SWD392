package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Employee;
import com.example.backend.enums.EmployeeStatus;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(String employeeID) {
        return employeeRepository.findById(employeeID);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByStatus(EmployeeStatus status) {
        return employeeRepository.findByStatus(status);
    }

    @Override
    public Employee updateEmployee(String employeeID, Employee employee) {
        employee.setEmployeeID(employeeID);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String employeeID) {
        employeeRepository.deleteById(employeeID);
    }

    @Override
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}