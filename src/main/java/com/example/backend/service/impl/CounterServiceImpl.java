package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Counter;
import com.example.backend.repository.Counter.CounterRepository;
import com.example.backend.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    // <editor-fold defaultstate="collapsed" desc="getNextUserID">
    @Override
    public String getNextUserID() {
        Counter counter = counterRepository.findById("userID").orElse(null);
        if (counter == null) {
            counter = new Counter();
            counter.setId("userID");
            counter.setSequence(1);
        } else {
            counter.setSequence(counter.getSequence() + 1);
        }
        counterRepository.save(counter);
        return String.format("%08d", counter.getSequence()); // Formats as "00000001"
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getNextEmployeeID">
    @Override
    public String getNextEmployeeID() {
        Counter counter = counterRepository.findById("employeeID").orElse(null);
        if (counter == null) {
            counter = new Counter();
            counter.setId("employeeID");
            counter.setSequence(1); // Start at 1
        } else {
            counter.setSequence(counter.getSequence() + 1);
        }
        counterRepository.save(counter);
        return String.format("1%07d", counter.getSequence()); // "10000001"
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getNextRestaurantID">
    @Override
    public String getNextRestaurantID() {
        Counter counter = counterRepository.findById("restaurantID").orElse(null);
        if (counter == null) {
            counter = new Counter();
            counter.setId("restaurantID");
            counter.setSequence(1);
        } else {
            counter.setSequence(counter.getSequence() + 1);
        }
        counterRepository.save(counter);
        return String.format("A%07d", counter.getSequence()); // "A0000001"
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="getNextApplicationID">
    @Override
    public String getNextApplicationID() {
        Counter counter = counterRepository.findById("applicationID").orElse(null);
        if (counter == null) {
            counter = new Counter();
            counter.setId("applicationID");
            counter.setSequence(1);
        } else {
            counter.setSequence(counter.getSequence() + 1);
        }
        counterRepository.save(counter);
        return String.format("B%07d", counter.getSequence()); // "B0000001"
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="updateCounter">
    @Override
    public void updateCounter(String counterId, long sequence) {
        Counter counter = counterRepository.findById(counterId).orElse(new Counter());
        counter.setId(counterId);
        counter.setSequence(sequence);
        counterRepository.save(counter);
    }
    // </editor-fold>
}
