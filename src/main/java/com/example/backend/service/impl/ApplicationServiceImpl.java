package com.example.backend.service.impl;

import com.example.backend.dto.request.Application.CreateApplicationDTO;
import com.example.backend.dto.request.Application.UpdateApplicationDTO;
import com.example.backend.enums.RoleName;
import com.example.backend.model.mongoDB.Application;
import com.example.backend.model.mongoDB.User;
import com.example.backend.repository.ApplicationRepository;
import com.example.backend.service.ApplicationService;
import com.example.backend.service.CounterService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CounterService counterService;

    @Autowired
    private UserService userService;

    // <editor-fold defaultstate="collapsed" desc="createApplication">
    @Override
    public Application createApplication(CreateApplicationDTO request) {
        // Validate managedByUserId using userID
        User manager = userService.findByUserID(request.getManagedByUserId());
        if (manager == null || !(manager.getRole() == RoleName.RESTAURANT_MANAGER || manager.getRole() == RoleName.STORE_MANAGER)) {
            throw new IllegalArgumentException("ManagedByUserId must be a valid RESTAURANT_MANAGER or STORE_MANAGER userID");
        }

        Application application = new Application();
        application.setApplicationID(counterService.getNextApplicationID());
        application.setManagedByUserId(manager.getId());
        application.setApplicationType(request.getApplicationType());
        application.setDescription(request.getDescription());
        application.setCreatedAt(LocalDateTime.now());
        application.setStatus(request.getStatus());
        application.setDateFrom(LocalDateTime.now());
        application.setDateTo(request.getDateTo() != null ? request.getDateTo() : LocalDateTime.now());

        return applicationRepository.save(application);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="updateApplication">
    @Override
    public Application updateApplication(String applicationId, UpdateApplicationDTO request) {
        Application application = getApplicationById(applicationId);
        if (application == null) {
            return null;
        }

        // Validate managedByUserId if changed
        if (request.getManagedByUserId() != null) {
            User manager = userService.getUserById(request.getManagedByUserId());
            if (manager == null || !(manager.getRole() == RoleName.STORE_MANAGER)) {
                throw new IllegalArgumentException("ManagedByUserId must be a RESTAURANT_MANAGER or STORE_MANAGER");
            }
            application.setManagedByUserId(request.getManagedByUserId());
        }

        if (request.getApplicationType() != null) application.setApplicationType(request.getApplicationType());
        if (request.getDescription() != null) application.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            application.setStatus(request.getStatus());
            // Update dateTo when status changes (e.g., confirmed or rejected)
            if (!request.getStatus().equals(application.getStatus())) {
                application.setDateTo(LocalDateTime.now());
            }
        }

        return applicationRepository.save(application);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="deleteApplication">
    @Override
    public boolean deleteApplication(String applicationId) {
        try {
            applicationRepository.deleteById(applicationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getApplicationById">
    @Override
    public Application getApplicationById(String applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getAllApplications">
    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // </editor-fold>
}