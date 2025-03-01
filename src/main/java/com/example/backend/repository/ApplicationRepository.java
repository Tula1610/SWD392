package com.example.backend.repository;

import com.example.backend.model.mongoDB.Application;
import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByManagedBy_UserID(String userID);
    List<Application> findByApplicationType(ApplicationType applicationType);  
    List<Application> findByStatus(ApplicationStatus status);  
}