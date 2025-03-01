package com.example.backend.model;

import com.example.backend.enums.RoleName;
import com.example.backend.model.mongoDB.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

     int roleID;

    @NotNull(message = "Role name is required")
     RoleName roleName;

    @Size(max = 255, message = "Description must be less than 255 characters")
     String description;

     Set<User> users;
}