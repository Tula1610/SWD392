package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.backend.enums.PeriodType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@Document(collection = "periods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Period {

    @Id
    String id;  // Đổi từ periodID -> id để theo chuẩn MongoDB

    @NotNull(message = "Period type is required")
    PeriodType periodType;

    @NotNull(message = "Year is required")
    long year;  // Dùng long thay vì int để xử lý giá trị lớn hơn

    @NotNull(message = "Total weeks worked is required")
    @PositiveOrZero(message = "Total weeks worked must be positive or zero")
    int totalWeeksWorked;

    @NotNull(message = "Total months worked is required")
    @PositiveOrZero(message = "Total months worked must be positive or zero")
    int totalMonthsWorked;

    @NotNull(message = "Total hours is required")
    @PositiveOrZero(message = "Total hours must be positive or zero")  // Bổ sung kiểm tra giá trị
    double totalHours;

    @NotNull(message = "Updated at is required")
    LocalDateTime updatedAt;

    @NotNull(message = "Start date is required")
    LocalDateTime startDate;

    @NotNull(message = "End date is required")
    LocalDateTime endDate;
}
