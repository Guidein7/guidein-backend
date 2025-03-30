package com.GuideIn.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse {
    // Getters and setters
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}