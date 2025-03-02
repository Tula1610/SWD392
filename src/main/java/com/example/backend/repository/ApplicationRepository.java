package com.example.backend.repository;

import com.example.backend.model.mongoDB.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
}