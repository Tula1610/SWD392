package com.example.backend.service;

public interface CounterService {
    String getNextUserID();
    String getNextEmployeeID();
    String getNextRestaurantID();
    String getNextApplicationID();

    void updateCounter(String counterId, long sequence);
}
