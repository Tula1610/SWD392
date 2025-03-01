package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Application;
import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import com.example.backend.repository.ApplicationRepository;
import com.example.backend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Optional<Application> getApplicationById(String applicationID) {
        return applicationRepository.findById(applicationID);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getApplicationsByManagerId(String userID) {
        return applicationRepository.findByManagedBy_UserID(userID);
    }

    @Override
    public List<Application> getApplicationsByType(ApplicationType applicationType) {
        return applicationRepository.findByApplicationType(applicationType);
    }

    @Override
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    @Override
    public Application updateApplication(String applicationID, Application application) {
        application.setApplicationID(applicationID);
        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(String applicationID) {
        applicationRepository.deleteById(applicationID);
    }
}