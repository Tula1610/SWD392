package com.example.backend.repository;

import com.example.backend.model.mongoDB.Period;
import com.example.backend.enums.PeriodType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PeriodRepository extends MongoRepository<Period, String> {
    List<Period> findByPeriodType(PeriodType periodType);
    List<Period> findByYear(int year);  
}