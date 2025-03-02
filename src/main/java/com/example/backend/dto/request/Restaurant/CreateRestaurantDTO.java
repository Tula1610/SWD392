package com.example.backend.dto.request.Restaurant;

import com.example.backend.enums.RestaurantStatus;
import lombok.Data;

@Data
public class CreateRestaurantDTO {
    private String location;
    private double rating;
    private RestaurantStatus status;
}
