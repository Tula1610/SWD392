package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Period;
import com.example.backend.enums.PeriodType;
import com.example.backend.repository.PeriodRepository;
import com.example.backend.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodServiceImpl implements PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    @Override
    public Period createPeriod(Period period) {
        return periodRepository.save(period);
    }

    @Override
    public Optional<Period> getPeriodById(String periodID) {
        return periodRepository.findById(periodID);
    }

    @Override
    public List<Period> getAllPeriods() {
        return periodRepository.findAll();
    }

    @Override
    public List<Period> getPeriodsByType(PeriodType periodType) {
        return periodRepository.findByPeriodType(periodType);
    }

    @Override
    public List<Period> getPeriodsByYear(int year) {
        return periodRepository.findByYear(year);
    }

    @Override
    public Period updatePeriod(String periodID, Period period) {
        period.setPeriodID(periodID);
        return periodRepository.save(period);
    }

    @Override
    public void deletePeriod(String periodID) {
        periodRepository.deleteById(periodID);
    }
}