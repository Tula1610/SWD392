package com.example.backend.service.impl;

import com.example.backend.dto.request.Restaurant.CreateRestaurantDTO;
import com.example.backend.dto.request.Restaurant.UpdateRestaurantDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.enums.RestaurantStatus;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.model.mongoDB.Restaurant;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.RestaurantRepository;
import com.example.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CounterServiceImpl counterService;

    // <editor-fold default state="collapsed" desc="deleteRestaurant">
    @Override
    public boolean deleteRestaurant(String restaurantId) {
        try {
            Restaurant restaurantToDelete = getRestaurantById(restaurantId);
            if (restaurantToDelete == null) {
                return false;
            }
            restaurantRepository.deleteById(restaurantId);

            // Fetch all remaining restaurants
            List<Restaurant> remainingRestaurants = restaurantRepository.findAll();

            // Reassign restaurantIDs sequentially starting from 1
            int newSequence = 1;
            for (Restaurant restaurant : remainingRestaurants) {
                restaurant.setRestaurantID(String.format("A%07d", newSequence));
                restaurantRepository.save(restaurant);
                newSequence++;
            }

            // Update the counter
            counterService.updateCounter("restaurantID", newSequence - 1);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="createRestaurant">
    @Override
    public Restaurant createRestaurant(CreateRestaurantDTO request) {
        Restaurant restaurant = new Restaurant();

        long nextSequence = restaurantRepository.count() + 1;
        restaurant.setRestaurantID(String.format("A%07d", nextSequence)); // "A0000001", "A0000002", etc.
        restaurant.setLocation(request.getLocation());
        restaurant.setRating(request.getRating());
        restaurant.setStatus(request.getStatus() != null ? request.getStatus() : RestaurantStatus.OPEN);

        counterService.updateCounter("restaurantID", nextSequence);
        return restaurantRepository.save(restaurant);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="updateRestaurant">
// For better error handling, you might want to:
    @Override
    public ResponseEntity<ApiResponse<Restaurant>> updateRestaurant(String restaurantId, UpdateRestaurantDTO request) {
        try {
            Restaurant restaurant = getRestaurantById(restaurantId);
            if (restaurant == null) {
                ApiResponse<Restaurant> response = new ApiResponse<>(false, "Restaurant not found with ID: " + restaurantId, null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
            }

            restaurant.setLocation(request.getLocation());
            restaurant.setRating(request.getRating());
            restaurant.setStatus(request.getStatus());

            // If status is set to CLOSED, clear employeeIds regardless of request
            if ("CLOSED".equalsIgnoreCase(restaurant.getStatus().name())) {
                restaurant.setEmployeeIds(null);
            } else {
                // Process employeeIds (assuming these are custom employeeIDs like "10000001")
                List<String> employeeIds = request.getEmployeeIds();
                if (employeeIds != null && !employeeIds.isEmpty()) {
                    // Convert custom employeeIDs to MongoDB _ids
                    List<Employee> existingEmployees = employeeRepository.findAllById(
                            employeeRepository.findAll().stream()
                                    .filter(emp -> employeeIds.contains(emp.getEmployeeID()))
                                    .map(Employee::getId)
                                    .collect(Collectors.toList())
                    );
                    if (existingEmployees.size() != employeeIds.size()) {
                        ApiResponse<Restaurant> response = new ApiResponse<>(false, "One or more employee IDs do not exist", null);
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
                    }
                    // Store MongoDB _ids, not custom employeeIDs
                    restaurant.setEmployeeIds(new HashSet<>(existingEmployees.stream().map(Employee::getId).collect(Collectors.toList())));
                } else {
                    restaurant.setEmployeeIds(null); // Clear if empty/null and not CLOSED
                }
            }

            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
            ApiResponse<Restaurant> response = new ApiResponse<>(true, "Restaurant updated successfully", updatedRestaurant);
            return new ResponseEntity<>(response, HttpStatus.OK); // 200
        } catch (Exception e) {
            ApiResponse<Restaurant> response = new ApiResponse<>(false, "Failed to update restaurant: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getRestaurantById">
    @Override
    public Restaurant getRestaurantById(String restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="findByLocation">
    @Override
    public Restaurant findByLocation(String location) {
        return restaurantRepository.findByLocation(location);
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="getAllRestaurants">
    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    // </editor-fold>


}
