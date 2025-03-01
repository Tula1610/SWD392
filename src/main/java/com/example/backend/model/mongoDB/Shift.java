package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@Document(collection = "shifts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift {

    @Id
    String id;  // Đổi từ shiftsID -> id theo chuẩn MongoDB

    @NotNull(message = "CreatedBy is required")
    String createdById;  // Lưu ID của User thay vì dùng @DBRef

    @NotNull(message = "Shift name is required")
    @Size(max = 255, message = "Shift name must be less than 255 characters")
    String shiftName;

    @NotNull(message = "Start time is required")
    LocalDateTime startTime;

    @NotNull(message = "End time is required")
    LocalDateTime endTime;

    @NotNull(message = "Break duration is required")
    @PositiveOrZero(message = "Break duration must be positive or zero")
    int breakDuration;

    Set<String> employeeIds;  // Lưu danh sách ID của Employee thay vì @DBRef
}
