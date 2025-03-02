package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.backend.enums.RestaurantStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@Document(collection = "restaurants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {

    @Id
    String id;

    @NotNull(message = "Restaurant ID is required")
    @Size(min = 8, max = 8, message = "Restaurant ID must be 8 characters")
    String restaurantID;

    @NotNull(message = "Location is required")
    @Size(max = 255, message = "Location must be less than 255 characters")
    String location;

    @NotNull(message = "Rating is required")
    @PositiveOrZero(message = "Rating must be positive or zero")
    double rating;  //

    @NotNull(message = "Status is required")
    RestaurantStatus status;

    Set<String> employeeIds;
}
