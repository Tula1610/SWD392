package com.example.backend.controller;

import com.example.backend.dto.request.Restaurant.CreateRestaurantDTO;
import com.example.backend.dto.request.Restaurant.UpdateRestaurantDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.ResponseData;
import com.example.backend.dto.response.Restaurant.RestaurantResponseDTO;
import com.example.backend.model.mongoDB.Employee;
import com.example.backend.model.mongoDB.Restaurant;
import com.example.backend.service.EmployeeService;
import com.example.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseData getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        // Transform into DTO with employee details
        List<RestaurantResponseDTO> renumberedRestaurants = new ArrayList<>();
        int newSequence = 1;
        for (Restaurant restaurant : restaurants) {
            RestaurantResponseDTO dto = new RestaurantResponseDTO();
            dto.setId(restaurant.getId());
            dto.setRestaurantID(String.format("A%07d", newSequence)); // Renumber as before
            dto.setLocation(restaurant.getLocation());
            dto.setRating(restaurant.getRating());
            dto.setStatus(restaurant.getStatus());

            // Fetch employee details if employeeIds exist
            if (restaurant.getEmployeeIds() != null && !restaurant.getEmployeeIds().isEmpty()) {
                List<Employee> employees = employeeService.getAllEmployees().stream()
                        .filter(emp -> restaurant.getEmployeeIds().contains(emp.getId()))
                        .collect(Collectors.toList());

                List<RestaurantResponseDTO.EmployeeDetailsDTO> employeeDetails = employees.stream()
                        .map(emp -> {
                            RestaurantResponseDTO.EmployeeDetailsDTO empDto = new RestaurantResponseDTO.EmployeeDetailsDTO();
                            empDto.setId(emp.getId());
                            empDto.setEmployeeID(emp.getEmployeeID());
                            empDto.setFirstName(emp.getFirstName());
                            empDto.setLastName(emp.getLastName());
                            empDto.setEmail(emp.getEmail());
                            return empDto;
                        })
                        .collect(Collectors.toList());
                dto.setEmployees(employeeDetails);
            } else {
                dto.setEmployees(null);
            }

            renumberedRestaurants.add(dto);
            newSequence++;
        }

        return new ResponseData(
                200, true,
                "Restaurant list fetched successfully",
                renumberedRestaurants, null, null);
    }

    @GetMapping("/{restaurantID}")
    public Restaurant getRestaurantById(@PathVariable String restaurantID) {
        return restaurantService.getRestaurantById(restaurantID);
    }

    @GetMapping("/location/{location}")
    public Restaurant getRestaurantByLocation(@PathVariable String location) {
        return restaurantService.findByLocation(location);
    }

    @PostMapping
    public ResponseEntity<List<Restaurant>> createRestaurant(@RequestBody List<CreateRestaurantDTO> restaurants) {
        List<Restaurant> createdRestaurants = new ArrayList<>();
        for (CreateRestaurantDTO dto : restaurants) {

            Restaurant newRestaurant = restaurantService.createRestaurant(dto);
            createdRestaurants.add(newRestaurant);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurants);
    }

    @PutMapping("/{restaurantID}")
    public ResponseEntity<ApiResponse<Restaurant>> updateRestaurant(
            @PathVariable String restaurantID,
            @RequestBody UpdateRestaurantDTO request) {
        return restaurantService.updateRestaurant(restaurantID, request);
    }

    @DeleteMapping("/{restaurantID}")
    public ResponseEntity<Boolean> deleteRestaurant(@PathVariable String restaurantID) {
        boolean isDeleted = restaurantService.deleteRestaurant(restaurantID);
        return isDeleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

}
