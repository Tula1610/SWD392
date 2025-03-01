package com.example.backend.service;

import com.example.backend.model.mongoDB.Application;
import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application createApplication(Application application);
    Optional<Application> getApplicationById(String applicationID);
    List<Application> getAllApplications();
    List<Application> getApplicationsByManagerId(String userID);
    List<Application> getApplicationsByType(ApplicationType applicationType);
    List<Application> getApplicationsByStatus(ApplicationStatus status);
    Application updateApplication(String applicationID, Application application);
    void deleteApplication(String applicationID);
}