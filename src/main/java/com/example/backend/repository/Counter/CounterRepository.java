package com.example.backend.repository.Counter;

import com.example.backend.model.mongoDB.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends MongoRepository<Counter, String> {
}