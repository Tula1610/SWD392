package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.backend.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@Document(collection = "employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
     String id;

    @NotNull(message = "Employee ID is required")
    @Size(min = 8, max = 8, message = "Employee ID must be 8 characters")
    String employeeID;

    @NotNull(message = "Status is required")
     EmployeeStatus status;

    @NotNull(message = "First name is required")
    @Size(max = 100, message = "First name must be less than 100 characters")
     String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 100, message = "Last name must be less than 100 characters")
     String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be less than 255 characters")
     String email;

    @DBRef
     Set<Attendance> attendances;

    @DBRef
     Set<Shift> shifts;
}