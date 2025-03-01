package com.example.backend.model.mongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.backend.enums.ApplicationStatus;
import com.example.backend.enums.ApplicationType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Data
@Document(collection = "applications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {

    @Id
    String id;

    @NotNull(message = "Managed by user ID is required")
    String managedByUserId;  // Lưu ID thay vì @DBRef

    @NotNull(message = "Application type is required")
    ApplicationType applicationType;

    String description;

    @NotNull(message = "Created at is required")
    LocalDateTime createdAt;

    @NotNull(message = "Status is required")
    ApplicationStatus status;

    @NotNull(message = "Date from is required")
    LocalDateTime dateFrom;

    @NotNull(message = "Date to is required")
    LocalDateTime dateTo;
}
