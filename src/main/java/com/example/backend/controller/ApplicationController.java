package com.example.backend.controller;

import com.example.backend.dto.request.Application.CreateApplicationDTO;
import com.example.backend.dto.request.Application.UpdateApplicationDTO;
import com.example.backend.dto.response.ApplicationResponseDTO;
import com.example.backend.dto.response.ResponseData;
import com.example.backend.model.mongoDB.Application;
import com.example.backend.model.mongoDB.User;
import com.example.backend.service.ApplicationService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseData getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();

        // Transform into DTO with manager details
        List<ApplicationResponseDTO> applicationDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationResponseDTO dto = new ApplicationResponseDTO();
            dto.setId(application.getId());
            dto.setApplicationID(application.getApplicationID());
            dto.setApplicationType(application.getApplicationType());
            dto.setDescription(application.getDescription());
            dto.setCreatedAt(application.getCreatedAt());
            dto.setStatus(application.getStatus());
            dto.setDateFrom(application.getDateFrom());
            dto.setDateTo(application.getDateTo());

            // Fetch manager details
            User manager = userService.getUserById(application.getManagedByUserId());
            if (manager != null) {
                ApplicationResponseDTO.ManagerDetailsDTO managerDto = new ApplicationResponseDTO.ManagerDetailsDTO();
                managerDto.setId(manager.getId());
                managerDto.setUserID(manager.getUserID());
                managerDto.setFirstName(manager.getFirstName());
                managerDto.setLastName(manager.getLastName());
                managerDto.setEmail(manager.getEmail());
                managerDto.setRole(manager.getRole().name()); // Convert RoleName enum to String
                dto.setManager(managerDto);
            } else {
                dto.setManager(null); // If manager not found
            }

            applicationDTOs.add(dto);
        }

        return new ResponseData(200, true, "Application list fetched successfully", applicationDTOs, null, null);
    }

    @GetMapping("/{applicationID}")
    public ResponseData getApplicationById(@PathVariable String applicationID) {
        Application application = applicationService.getApplicationById(applicationID);
        if (application == null) {
            return new ResponseData(404, false, "Application not found with ID: " + applicationID, null, null, null);
        }

        // Transform into DTO with manager details
        ApplicationResponseDTO dto = new ApplicationResponseDTO();
        dto.setId(application.getId());
        dto.setApplicationID(application.getApplicationID());
        dto.setApplicationType(application.getApplicationType());
        dto.setDescription(application.getDescription());
        dto.setCreatedAt(application.getCreatedAt());
        dto.setStatus(application.getStatus());
        dto.setDateFrom(application.getDateFrom());
        dto.setDateTo(application.getDateTo());

        User manager = userService.getUserById(application.getManagedByUserId());
        if (manager != null) {
            ApplicationResponseDTO.ManagerDetailsDTO managerDto = new ApplicationResponseDTO.ManagerDetailsDTO();
            managerDto.setId(manager.getId());
            managerDto.setUserID(manager.getUserID());
            managerDto.setFirstName(manager.getFirstName());
            managerDto.setLastName(manager.getLastName());
            managerDto.setEmail(manager.getEmail());
            managerDto.setRole(manager.getRole().name());
            dto.setManager(managerDto);
        } else {
            dto.setManager(null);
        }

        return new ResponseData(200, true, "Application fetched successfully", dto, null, null);
    }

    @PostMapping
    public ResponseEntity<List<Application>> createApplications(@RequestBody List<CreateApplicationDTO> applications) {
        List<Application> createdApplications = new ArrayList<>();
        for (CreateApplicationDTO dto : applications) {
            Application newApplication = applicationService.createApplication(dto);
            createdApplications.add(newApplication);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplications);
    }

    @PutMapping("/{applicationID}")
    public ResponseEntity<ResponseData> updateApplication(
            @PathVariable String applicationID,
            @RequestBody UpdateApplicationDTO request) {
        try {
            Application updatedApplication = applicationService.updateApplication(applicationID, request);
            if (updatedApplication == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseData(404, false, "Application not found with ID: " + applicationID, null, null, null));
            }
            return ResponseEntity.ok(new ResponseData(200, true, "Application updated successfully", updatedApplication, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData(500, false, "Failed to update application: " + e.getMessage(), null, null, null));
        }
    }

    @DeleteMapping("/{applicationID}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String applicationID) {
        boolean deleted = applicationService.deleteApplication(applicationID);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}