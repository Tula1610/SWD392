package com.example.backend.dto.response.Restaurant;

import com.example.backend.enums.RestaurantStatus;

import lombok.Data;

import java.util.List;


@Data
public class RestaurantResponseDTO {
    private String id;
    private String restaurantID;
    private String location;
    private double rating;
    private RestaurantStatus status;
    private List<EmployeeDetailsDTO> employees; // Detailed employee info

    @Data
    public static class EmployeeDetailsDTO {
        private String id; // MongoDB _id
        private String employeeID; // "10000001"
        private String firstName;
        private String lastName;
        private String email;
    }
}