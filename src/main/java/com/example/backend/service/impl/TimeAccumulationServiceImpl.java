package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.TimeAccumulation;
import com.example.backend.repository.TimeAccumulationRepository;
import com.example.backend.service.TimeAccumulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TimeAccumulationServiceImpl implements TimeAccumulationService {

    @Autowired
    private TimeAccumulationRepository timeAccumulationRepository;

    @Override
    public TimeAccumulation createTimeAccumulation(TimeAccumulation timeAccumulation) {
        return timeAccumulationRepository.save(timeAccumulation);
    }

    @Override
    public Optional<TimeAccumulation> getTimeAccumulationById(String accumulationID) {
        return timeAccumulationRepository.findById(accumulationID);
    }

    @Override
    public List<TimeAccumulation> getAllTimeAccumulations() {
        return timeAccumulationRepository.findAll();
    }

    @Override
    public TimeAccumulation updateTimeAccumulation(String accumulationID, TimeAccumulation timeAccumulation) {
        timeAccumulation.setAccumulationID(accumulationID);
        return timeAccumulationRepository.save(timeAccumulation);
    }

    @Override
    public void deleteTimeAccumulation(String accumulationID) {
        timeAccumulationRepository.deleteById(accumulationID);
    }
}