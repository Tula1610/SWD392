package com.example.backend.dto.response;

import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponseDTO {
    private String id;
    private String applicationID;
    private ManagerDetailsDTO manager; // Manager details instead of just managedByUserId
    private ApplicationType applicationType;
    private String description;
    private LocalDateTime createdAt;
    private ApplicationStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    @Data
    public static class ManagerDetailsDTO {
        private String id; // MongoDB _id
        private String userID; // "00000001"
        private String firstName;
        private String lastName;
        private String email;
        private String role; // RoleName as String
    }
}