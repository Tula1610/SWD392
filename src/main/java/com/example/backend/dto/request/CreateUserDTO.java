package com.example.backend.dto.request;

import com.example.backend.enums.RoleName;

import lombok.Data;


@Data
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private RoleName role;

}
