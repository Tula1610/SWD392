package com.example.backend.service;

import com.example.backend.model.mongoDB.TimeAccumulation;
import java.util.List;
import java.util.Optional;

public interface TimeAccumulationService {
    TimeAccumulation createTimeAccumulation(TimeAccumulation timeAccumulation);
    Optional<TimeAccumulation> getTimeAccumulationById(String accumulationID);
    List<TimeAccumulation> getAllTimeAccumulations();
    TimeAccumulation updateTimeAccumulation(String accumulationID, TimeAccumulation timeAccumulation);
    void deleteTimeAccumulation(String accumulationID);
}