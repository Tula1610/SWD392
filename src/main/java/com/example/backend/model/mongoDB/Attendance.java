package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@Document(collection = "attendances")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {

    @Id
    String id;

    @NotNull(message = "Check in is required")
    LocalDateTime checkIn;

    LocalDateTime checkOut;

    @NotNull(message = "Break time is required")
    @PositiveOrZero(message = "Break time must be positive or zero")
    int breakTime;

    @Size(max = 100, message = "Note must be less than 100 characters")
    String note;

    @NotNull(message = "Employee ID is required")
    String employeeId;  // Lưu ID thay vì @DBRef

    @NotNull(message = "Shift ID is required")
    String shiftId;  // Lưu ID thay vì @DBRef
}
