package com.example.backend.controller;

import com.example.backend.model.mongoDB.Restaurant;
import com.example.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantID}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String restaurantID) {
        return restaurantService.getRestaurantById(restaurantID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<Restaurant> getRestaurantByLocation(@PathVariable String location) {
        Restaurant restaurant = restaurantService.findByLocation(location);
        return restaurant != null ? ResponseEntity.ok(restaurant) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{restaurantID}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String restaurantID, @RequestBody Restaurant restaurant) {
        try {
            Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantID, restaurant);
            return ResponseEntity.ok(updatedRestaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{restaurantID}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String restaurantID) {
        restaurantService.deleteRestaurant(restaurantID);
        return ResponseEntity.ok().build();
    }
}