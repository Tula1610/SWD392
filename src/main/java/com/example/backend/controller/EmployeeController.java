package com.example.backend.controller;

import com.example.backend.model.mongoDB.Employee;
import com.example.backend.enums.EmployeeStatus;
import com.example.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeID) {
        return employeeService.getEmployeeById(employeeID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Employee> getEmployeesByStatus(@PathVariable EmployeeStatus status) {
        return employeeService.getEmployeesByStatus(status);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        Employee employee = employeeService.findByUsername(username);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findByEmail(email);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{employeeID}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeID, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeID, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeID}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeID) {
        employeeService.deleteEmployee(employeeID);
        return ResponseEntity.ok().build();
    }
}