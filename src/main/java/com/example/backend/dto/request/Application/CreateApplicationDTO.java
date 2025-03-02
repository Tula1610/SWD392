package com.example.backend.dto.request.Application;

import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationDTO {
    private String managedByUserId;
    private ApplicationType applicationType;
    private String description;
    private ApplicationStatus status;
    private LocalDateTime dateTo;
}
