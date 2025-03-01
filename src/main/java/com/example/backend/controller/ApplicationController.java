package com.example.backend.controller;

import com.example.backend.model.mongoDB.Application;
import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import com.example.backend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{applicationID}")
    public ResponseEntity<Application> getApplicationById(@PathVariable String applicationID) {
        return applicationService.getApplicationById(applicationID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/manager/{userID}")
    public List<Application> getApplicationsByManagerId(@PathVariable String userID) {
        return applicationService.getApplicationsByManagerId(userID);
    }

    @GetMapping("/type/{applicationType}")
    public List<Application> getApplicationsByType(@PathVariable ApplicationType applicationType) {
        return applicationService.getApplicationsByType(applicationType);
    }

    @GetMapping("/status/{status}")
    public List<Application> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        return applicationService.getApplicationsByStatus(status);
    }

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    @PutMapping("/{applicationID}")
    public ResponseEntity<Application> updateApplication(@PathVariable String applicationID, @RequestBody Application application) {
        try {
            Application updatedApplication = applicationService.updateApplication(applicationID, application);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{applicationID}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String applicationID) {
        applicationService.deleteApplication(applicationID);
        return ResponseEntity.ok().build();
    }
}