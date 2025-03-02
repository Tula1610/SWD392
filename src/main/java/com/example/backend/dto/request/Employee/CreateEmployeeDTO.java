package com.example.backend.dto.request.Employee;

import com.example.backend.enums.EmployeeStatus;
import lombok.Data;

@Data
public class CreateEmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private EmployeeStatus status;
}
