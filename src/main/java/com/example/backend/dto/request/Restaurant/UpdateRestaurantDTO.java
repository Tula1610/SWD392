package com.example.backend.dto.request.Restaurant;

import com.example.backend.enums.RestaurantStatus;
import com.example.backend.model.mongoDB.Employee;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRestaurantDTO {
    private String location;
    private double rating;
    private RestaurantStatus status;
    private List<String> employeeIds;
}
