package com.example.backend.service;

import com.example.backend.model.mongoDB.Period;
import com.example.backend.enums.PeriodType;
import java.util.List;
import java.util.Optional;

public interface PeriodService {
    Period createPeriod(Period period);
    Optional<Period> getPeriodById(String periodID);
    List<Period> getAllPeriods();
    List<Period> getPeriodsByType(PeriodType periodType);
    List<Period> getPeriodsByYear(int year);
    Period updatePeriod(String periodID, Period period);
    void deletePeriod(String periodID);
}