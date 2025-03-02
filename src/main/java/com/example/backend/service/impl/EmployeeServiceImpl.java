package com.example.backend.service.impl;

import com.example.backend.dto.request.Employee.CreateEmployeeDTO;
import com.example.backend.dto.request.Employee.UpdateEmployeeDTO;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.service.CounterService;
import com.example.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CounterService counterService;

    // <editor-fold default state="collapsed" desc="createEmployee">
    @Override
    public Employee createEmployee(CreateEmployeeDTO request) {
        Employee employee = new Employee();

        long nextSequence = employeeRepository.count() + 1;
        employee.setEmployeeID(String.format("1%07d", nextSequence));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setStatus(request.getStatus());

        counterService.updateCounter("employeeID", nextSequence);
        return employeeRepository.save(employee);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="updateEmployee">
    @Override
    public Employee updateEmployee(String employeeId, UpdateEmployeeDTO request) {
        try {

            Employee employee = getEmployeeById(employeeId);
            if (employee == null) {
                return null;
            }

            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());
            employee.setEmail(request.getEmail());
            employee.setStatus(request.getStatus());

            return employeeRepository.save(employee);
        } catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="deleteEmployee">
    @Override
    public boolean deleteEmployee(String employeeId) {
        try {
            Employee employeeToDelete = getEmployeeById(employeeId);
            if (employeeToDelete == null) {
                return false;
            }
            employeeRepository.deleteById(employeeId);

            // Fetch all remaining employees
            List<Employee> remainingEmployees = employeeRepository.findAll();

            // Reassign employeeIDs sequentially starting from 1
            int newSequence = 1;
            for (Employee employee : remainingEmployees) {
                employee.setEmployeeID(String.format("1%07d", newSequence));
                employeeRepository.save(employee);
                newSequence++;
            }

            // Update the counter
            counterService.updateCounter("employeeID", newSequence - 1);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getEmployeeById">
    @Override
    public Employee getEmployeeById(String employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getAllEmployees">
    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="findByEmail">
    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="null">

    // </editor-fold>
}
