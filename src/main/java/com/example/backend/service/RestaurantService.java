package com.example.backend.service;

import com.example.backend.dto.request.Restaurant.CreateRestaurantDTO;
import com.example.backend.dto.request.Restaurant.UpdateRestaurantDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.model.mongoDB.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(CreateRestaurantDTO request);
    ResponseEntity<ApiResponse<Restaurant>> updateRestaurant(String restaurantId, UpdateRestaurantDTO request);
    boolean deleteRestaurant(String restaurantId);

    Restaurant getRestaurantById(String restaurantId);
    Restaurant findByLocation(String location);
    List<Restaurant> getAllRestaurants();
}
