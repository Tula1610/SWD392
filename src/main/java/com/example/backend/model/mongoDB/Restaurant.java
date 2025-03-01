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
    String id;  // Đổi từ restaurantID -> id theo chuẩn MongoDB

    @NotNull(message = "Location is required")
    @Size(max = 255, message = "Location must be less than 255 characters")
    String location;

    @NotNull(message = "Rating is required")
    @PositiveOrZero(message = "Rating must be positive or zero")  // Đảm bảo giá trị hợp lệ
    double rating;  // Dùng double thay vì String để lưu rating

    @NotNull(message = "Status is required")
    RestaurantStatus status;

    Set<String> employeeIds;  // Lưu danh sách ID thay vì sử dụng @DBRef
}
