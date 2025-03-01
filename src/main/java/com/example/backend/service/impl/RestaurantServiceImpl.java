package com.example.backend.service.impl;

import com.example.backend.model.mongoDB.Restaurant;
import com.example.backend.repository.RestaurantRepository;
import com.example.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String restaurantID) {
        return restaurantRepository.findById(restaurantID);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(String restaurantID, Restaurant restaurant) {
        restaurant.setRestaurantID(restaurantID);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(String restaurantID) {
        restaurantRepository.deleteById(restaurantID);
    }

    @Override
    public Restaurant findByLocation(String location) {
        return restaurantRepository.findByLocation(location);
    }
}