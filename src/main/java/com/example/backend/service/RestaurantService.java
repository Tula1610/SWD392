package com.example.backend.service;

import com.example.backend.model.mongoDB.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Optional<Restaurant> getRestaurantById(String restaurantID);
    List<Restaurant> getAllRestaurants();
    Restaurant updateRestaurant(String restaurantID, Restaurant restaurant);
    void deleteRestaurant(String restaurantID);
    Restaurant findByLocation(String location);
}