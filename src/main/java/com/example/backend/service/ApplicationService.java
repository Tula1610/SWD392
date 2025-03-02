package com.example.backend.service;

import com.example.backend.dto.request.Application.CreateApplicationDTO;
import com.example.backend.dto.request.Application.UpdateApplicationDTO;
import com.example.backend.model.mongoDB.Application;
import java.util.List;

public interface ApplicationService {
    Application createApplication(CreateApplicationDTO request);
    Application updateApplication(String applicationId, UpdateApplicationDTO request);
    boolean deleteApplication(String applicationId);
    Application getApplicationById(String applicationId);
    List<Application> getAllApplications();
}