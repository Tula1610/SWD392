package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@Document(collection = "timeAccumulations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeAccumulation {

    @Id
    String id;  // Đổi từ accumulationID -> id theo chuẩn MongoDB

    @NotNull(message = "Attendance ID is required")
    String attendanceId;  // Lưu ID thay vì lưu toàn bộ object Attendance

    @NotNull(message = "Total hours by day is required")
    @PositiveOrZero(message = "Total hours must be positive or zero")
    double totalHoursByDay;

    @NotNull(message = "Updated at is required")
    LocalDateTime updatedAt;
}
