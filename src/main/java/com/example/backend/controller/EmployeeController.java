package com.example.backend.controller;


import com.example.backend.dto.request.Employee.CreateEmployeeDTO;
import com.example.backend.dto.request.Employee.UpdateEmployeeDTO;
import com.example.backend.dto.response.ResponseData;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseData getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        // Renumber employeeIDs for the response
        List<Employee> renumberedEmployees = new ArrayList<>();
        int newSequence = 1;
        for (Employee employee : employees) {
            Employee renumberedEmployee = new Employee();
            renumberedEmployee.setId(employee.getId());
            renumberedEmployee.setEmployeeID(String.format("1%07d", newSequence)); // "10000001", etc.
            renumberedEmployee.setStatus(employee.getStatus());
            renumberedEmployee.setFirstName(employee.getFirstName());
            renumberedEmployee.setLastName(employee.getLastName());
            renumberedEmployee.setEmail(employee.getEmail());
            renumberedEmployee.setAttendances(employee.getAttendances());
            renumberedEmployee.setShifts(employee.getShifts());
            renumberedEmployees.add(renumberedEmployee);
            newSequence++;
        }

        return new ResponseData(
                200, true,
                "Employee list fetched successfully",
                renumberedEmployees, null, null);
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable String employeeID) {
        return employeeService.getEmployeeById(employeeID);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findByEmail(email);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<CreateEmployeeDTO> employees) {
        List<Employee> createdEmployees = new ArrayList<>();

        for (CreateEmployeeDTO dto : employees) {
            Employee newEmployee = employeeService.createEmployee(dto);
            createdEmployees.add(newEmployee);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployees);
    }


    @PutMapping("/{employeeID}")
    public ResponseEntity<ResponseData> updateEmployee(@PathVariable String employeeID,
                                                       @RequestBody UpdateEmployeeDTO request) {
        try {
            Employee employee = employeeService.updateEmployee(employeeID, request);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData(404, false, "Employee not found", null, null, null));
            }
            return ResponseEntity.ok(new ResponseData(200, true, "Employee updated successfully", employee, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData(500, false, "Failed to update employee: " + e.getMessage(), null, null, null));
        }
    }

    @DeleteMapping("/{employeeID}")
    public boolean deleteEmployee(@PathVariable String employeeID) {
        return employeeService.deleteEmployee(employeeID);
    }
}
