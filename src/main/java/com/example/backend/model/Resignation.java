package com.example.backend.model;

import com.example.backend.enums.ResignationStatus;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resignation {

     int resignationID;

     EmployeeApplication employeeApplication;

     String resignReason;

     LocalDateTime lastWorkingDate;

     ResignationStatus status;
}