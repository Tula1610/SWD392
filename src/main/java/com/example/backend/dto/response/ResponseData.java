package com.example.backend.dto.response;

import com.example.backend.enums.RoleName;
import lombok.Data;

/**
 *    {
 *        status: 200
 *        description:
 *        data:
 *    }
 */
@Data
public class ResponseData {
    private int status = 200;
    private boolean isSuccess = true;
    private String description;
    private Object data;
    private RoleName role;
    private String redirectUrl;
}
